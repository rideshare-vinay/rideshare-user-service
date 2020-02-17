package com.revature.services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.revature.beans.User;
import com.revature.beans.googlemaps.DistanceMatrix;
import com.revature.exceptions.GoogleApiException;
import com.revature.services.RideRecommendationsService;
import com.revature.services.UserService;


@Service
public class RideRecommendationsServiceImpl implements RideRecommendationsService {
	
	// MIN_SEARCH_RADIUS is the minimum distance between two users (in km) for which we will actually hit the google maps api.
	// anything closer and there is simply no need.
	private static final double MIN_SEARCH_RADIUS=0.2d;
	
	// MAX_SEARCH_RADIUS is the maximum distance (in km) between riders where we will consider a recommendation.
	private static final double MAX_SEARCH_RADIUS=20.0d;
	
	private String apiKey = "";
	
	private RestTemplate restTemplate = new RestTemplate();
	
	
	@Autowired
	private UserService us;
	
	private List<User> riderCandidates = new ArrayList<>();
	private List<User> recommendations = new ArrayList<>();
	private List<Double> recommendationsDistance=new ArrayList<>();
	private List<User> apiUsers = new ArrayList<>();
	private List<Double> apiDist = new ArrayList<>();
	private int currentRecommendations;
	
	private DistanceMatrix results=null;
	
	/**
	 * 
	 *  We just read our api key from a file.
	 *  This would be in the constructor...but with @autowired, the constructor never gets called
	 * 
	 */
	public void getApiKey(){
		if(!apiKey.equals("")) {
			return;
		}
		BufferedReader br = null;
		try {
			// Doesn't work inside a jar file.
			File apiFile = new File(getClass().getClassLoader().getResource("apikey.txt").getFile());
			br = new BufferedReader(new FileReader(apiFile));
			apiKey=br.readLine();
			
		} catch (FileNotFoundException e) {
			System.err.println("file not found");
			apiKey="";
		} catch (IOException e) {
			System.err.println("io exception");
			apiKey="";
		} finally {
			try {
				if(br!=null) {
					br.close();
				}
			} catch (IOException e) {}
		}
		
	}
	
	/**
	 *  
	 *  getRideRecommendations returns a maximum number of recommendations equal to or less than numRecommendations 
	 *  that live closest to user (if there are not enough candidates that live close enough, fewer are returned).
	 *  If user is a driver, function returns candidates that are not drivers.
	 *  If user is not a driver, function returns drivers.
	 *  
	 *  @user -- user for whom we are getting recommendations 
	 *  @numRecommendations -- how many recommendations to return
	 *  @returns -- list of users recommended by algorithm
	 *  
	 *  Modification History:
	 *  
	 * 		Written: 10Feb2020
	 * 
	 */
	@Override
	public List<User> getRideRecommendations(User user, int numRecommendations) {
		if (numRecommendations<1) {
			return new ArrayList<>();
		}
		
		initializeRecommendations(user);
		preprocessRecommendations(user, numRecommendations);
		
		//at this point we have currentRecommendations / recommendaionsDistance filled with users that do not require api calls
		//and apiUsers / apiDist are filled with the users that qualify for api calls... so lets make that api call
		boolean apiCallSuccess=makeApiCall(user);
		// now lets merge those lists together
		mergeLists(apiCallSuccess, numRecommendations);
		
		return recommendations;
	}
	
	/**
	 * initializeRecommendations just creates our processing variables and grabs our candidate for recommendation form the DB
	 * 
	 * @param user -- user we are getting recommendations for
	 */
	public void initializeRecommendations(User user) {
		User tempUser=us.getUserById(user.getUserId());
		riderCandidates = us.getUserByRoleAndLocation(!(user.isDriver()), tempUser.getBatch().getBatchLocation());
		recommendations = new ArrayList<>();
		recommendationsDistance=new ArrayList<>();
		currentRecommendations=0;
		apiUsers = new ArrayList<>();
		apiDist = new ArrayList<>();
		
	}
	
	/**
	 * makeApiCall just passes our list of pre-processed users along to get a distance matrix from google maps
	 * 
	 * @param user -- user we are getting recommendations for
	 */
	public boolean makeApiCall(User user) {
		boolean apiCallSuccess=true;
		
		try {
			results = multipleUserDistance(user, apiUsers);
		} catch (Exception e) {
			apiCallSuccess=false;
		}
		if(results==null || !results.getStatus().equals("OK")) {
			apiCallSuccess=false;
		}
		return apiCallSuccess;
	}
	
	/**
	 * preprocessRecommendations takes our list of all riderCandidates and sorts them to three sets by straight line distance
	 * 		-- farther than MAX_SEARCH_RADIUS - trash can
	 * 		-- closer than MIN_SEARCH_RADIUS - straight to our recommendations
	 * 		-- between min and max - apiUsers list for further processing by google maps api.  also save straight line calculation
	 * 		   in apiDist so we can use it later in case of an api call failure.
	 * 
	 * @param user
	 * @param numRecommendations
	 */
	public void preprocessRecommendations(User user, int numRecommendations){
		for(User u:riderCandidates) {
			if(!u.isAcceptingRides() || !u.isActive()) {
				continue;
			}
			Double distance;
			distance=calculateKilometersBetweenPoints(user.getLatitude(), user.getLongitude(), u.getLatitude(), u.getLongitude());
			if(distance<MAX_SEARCH_RADIUS) {
				if(distance>MIN_SEARCH_RADIUS) {
					apiDist.add(distance);
					apiUsers.add(u);
				}else {
					if(currentRecommendations==0) {
						insertFirstElement(distance, u);
					}else {
						insertElement(distance, u);
						trimRecommendations(numRecommendations);
					}
				}
			}
		}
	}
	
	
	/**
	 * insertFirstElement -- helper function for preprocessRecommendations.  Just adds the first user, distance to empty lists.
	 * 
	 * @param distance -- distance to add to distance list
	 * @param u -- user to add to user list
	 */
	public void insertFirstElement(double distance, User u) {
		recommendations.add(u);
		recommendationsDistance.add(distance);
		currentRecommendations++;
	}
	
	/**
	 * insertElement -- helper function for preprocessRecommendations.  Finds spot to insert our user and distance into 
	 * their corresponding lists.  It's a simple insertion sort into a list which is known to already be in order.
	 * 
	 * @param distance -- distance to insert
	 * @param u -- user to insert
	 */
	public void insertElement(double distance, User u) {
		boolean added=false;
		for(int i=0; i<currentRecommendations; i++) {
			if(distance<recommendationsDistance.get(i)) {
				recommendations.add(i,u);
				recommendationsDistance.add(i,distance);
				currentRecommendations++;
				added=true;
				break;
			}
		}
		if (!added) {
			// we get here if the user still hasn't been added to our recommendations list yet.
			// this happens only when the distance is larger than any user already on the list so we just add it to the end.
			recommendations.add(u);
			recommendationsDistance.add(distance);
			currentRecommendations++;
		}
	}
	
	
	/**
	 * trimRecommendations checks to see if our list exceeds the requested number of recommendations and throws out the last 
	 * element if it is. 
	 * 
	 * @param numRecommendations -- max number of elements to keep
	 */
	public void trimRecommendations(int numRecommendations) {
		if (currentRecommendations>numRecommendations) {
			recommendations.remove(numRecommendations);
			recommendationsDistance.remove(numRecommendations);
			currentRecommendations--;
		}
	}
	
	/**
	 * mergeLists -- merges the list of users with api calculated distances into the list of users already recommended
	 * due to being closer than MIN_SEARCH_RADIUS
	 * 
	 * @param apiCallSuccess
	 * @param numRecommendations
	 */
	public void mergeLists(boolean apiCallSuccess, int numRecommendations) {
		int apiIndex=0;
		for (User u:apiUsers) {
			if(currentRecommendations==0) {
				mergeFirstElement(apiCallSuccess, u);
				apiIndex++;
			}else {
				mergeElement(apiCallSuccess, u, apiIndex);
				apiIndex++;
				trimRecommendations(numRecommendations);
			}
		}
	}
	
	/**
	 * mergeFirstElement -- helper function for merge lists.  Just merges first user into recommendations if the list is empty.
	 * 
	 * @param apiCallSuccess -- flag to let us know if the api call failed
	 * @param u -- user to add to list
	 */
	public void mergeFirstElement(boolean apiCallSuccess, User u) {
		recommendations.add(u);
		if(!apiCallSuccess || !results.getRows().get(0).getElements().get(0).getStatus().equals("OK")) {
			recommendationsDistance.add(apiDist.get(0));
		}else {
			recommendationsDistance.add(results.getRows().get(0).getElements().get(0).getDistance().getValue()/1000d);
		}
		currentRecommendations++;
	}
	
	/**
	 * mergeElement -- helper function for merge lists.  merges in any element other than the first one into recommendations.
	 *
	 * @param apiCallSuccess -- status of api call
	 * @param u -- user to add to list
	 * @param apiIndex -- our current position in the api processed list
	 */
	public void mergeElement(boolean apiCallSuccess, User u, int apiIndex) {
		Double distance;
		if(!apiCallSuccess || !results.getRows().get(0).getElements().get(apiIndex).getStatus().equals("OK")) {
			distance=apiDist.get(apiIndex);
		}else {
			distance=results.getRows().get(0).getElements().get(apiIndex).getDistance().getValue()/1000d;
		}
		
		boolean added=false;
		for(int i=0; i<currentRecommendations; i++) {
			if(distance<recommendationsDistance.get(i)) {
				recommendations.add(i,u);
				recommendationsDistance.add(i,distance);
				currentRecommendations++;
				added=true;
				break;
			}
		}
		if (!added) {
			// we get here if the user still hasn't been added to our recommendations list yet.
			// this happens only when the distance is larger than any user already on the list so we just add it to the end.
			recommendations.add(u);
			recommendationsDistance.add(distance);
			currentRecommendations++;
		}
	}
	/**
	 * 
	 * Gets DistanceMatrix object defining all the distances between user and every member of dUsers.
	 * Constructs the string to make the api call
	 * 
	 * @param user -- origin for the distance calculation
	 * @param dUsers -- multiple possible destination users for user
	 * @return -- the whole object defining all of the distances and drive durations from user to the dUsers
	 * @throws ExecutionException 
	 */
	public DistanceMatrix multipleUserDistance(User user, List<User>dUsers) throws ExecutionException {
		if (dUsers.isEmpty()) {
			//nothing passed in so nothing returned.
			return null;	
		}
		boolean firstElement=true;
		StringBuilder coordinates = new StringBuilder("origins="+user.getLatitude()+","+user.getLongitude()+"&destinations=");
		for(User u:dUsers) {
			if(!firstElement) {
				coordinates.append("|");
			}
			firstElement=false;
			coordinates.append(u.getLatitude()+","+u.getLongitude());
		}
		
		return distanceMatrix.get(coordinates.toString());
		
	}
	
	/**
	 * 
	 *  calculateKilometersBetweenPoints returns the straight line distance between a pair of lat, long coordinates.
	 *  The formula takes into account the narrowing of a degree longitude as latitude approaches +-90 degrees.
	 * 
	 *  @lat1 -- latitude of first coordinate
	 *  @long1 -- longitude of first coordinate
	 *  @lat2 -- latitude of second coordinate
	 *  @long2 -- longitude of second coordinate
	 *  @returns -- straight line distance between provided coordinates
	 */
	@Override
	public double calculateKilometersBetweenPoints(double lat1, double long1, double lat2, double long2) {
		final double radiusOfEarthKm=6378d;
		final double kmPerDegreeLat=111d;
		double kmPerDegreeLong = (Math.PI/180d)*radiusOfEarthKm*Math.cos(lat1*Math.PI/180d);
		double kmLatDistance=(lat1-lat2)*kmPerDegreeLat;
		double kmLongDistance=(long1-long2)*kmPerDegreeLong;
		return Math.sqrt(Math.pow(kmLatDistance,2d)+Math.pow(kmLongDistance,2d));
	}
	
	
	/**
	 * 
	 *  This code block initializes a cache for future calls to the Google Maps Distance Matrix API.
	 *  Calls can be made to distanceMatrix.get()
	 *  Returns a DistanceMatrix object which at this time has been trimmed down to provide distance and duration data but could be
	 *  	expanded by editing the DistanceMatrix**** set of POJOs in com.revature.beans.googlemaps
	 *  
	 *  @coordinates -- coordinates of request composed into a string portion of the api call.
	 *  	-- it is necessary to pass the data in this way because the parameters must function as the string key
	 *  	   of the key/value pairs stored in our cache.
	 *  @returns -- DistanceMatrix object containing json information from api call
	 */
	private LoadingCache<String, DistanceMatrix> distanceMatrix = CacheBuilder.newBuilder()
			  .maximumSize(2000)
			  .expireAfterAccess(720, TimeUnit.HOURS)
			  .build(new CacheLoader<String, DistanceMatrix>() {
	    @Override
	    public DistanceMatrix load(String coordinates) {
	    	getApiKey();
	    	if(apiKey.equals("")) {
	    		return null;
	    	}
	    	String distanceMatrixUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&"
	  			  + coordinates+"&key="+apiKey;
	    	DistanceMatrix response = restTemplate.getForObject(distanceMatrixUrl, DistanceMatrix.class);
	    	if (response != null) {
	    		return response;
	    	} else {
	    		throw new GoogleApiException("Google API Exception: Unable to find a route for coordinates: " + coordinates);
	    	}
	    }
	});

	/**
	 * 
	 * 	Function returns road distance between two sets of lat/long coordinates in kilometers.
	 * 	Uses google maps api through use of distanceMatrix.get()
	 * 	Conversion of the two sets of lat/long into a string is to fit into the key/value pairing of the cache used by the function.
	 * 
	 *  @throws ExecutionException 
	 *  @lat1 -- latitude of first coordinate
	 *  @long1 -- longitude of first coordinate
	 *  @lat2 -- latitude of second coordinate
	 *  @long2 -- longitude of second coordinate
	 *  @returns -- road distance value in kilometers between two coordinates
	 */
	@Override
	public double roadKilometersBetweenPoints(double lat1, double long1, double lat2, double long2) throws ExecutionException{
		String coordinates = "origins="+lat1+","+long1+"&destinations="+lat2+","+long2;
		return distanceMatrix.get(coordinates).getRows().get(0).getElements().get(0).getDistance().getValue()/1000d;
		  
	}
}
