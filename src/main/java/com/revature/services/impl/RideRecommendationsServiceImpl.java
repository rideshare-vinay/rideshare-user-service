package com.revature.services.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
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
	
	// minSearchRadius is the minimum distance between two users (in km) for which we will actually hit the google maps api.
	// anything closer and there is simply no need.
	private final double minSearchRadius=0.2d;
	
	// maxSearchRadius is the maximum distance between riders where we will consider a recommendation.
	private final double maxSearchRadius=20.0d;
	
	private String apiKey = "";
	
	private RestTemplate restTemplate = new RestTemplate();
	
	
	@Autowired
	private UserService us;
	
	/**********************************
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
			br = new BufferedReader(new FileReader("/apikey.txt"));
			apiKey=br.readLine();
			
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			apiKey="";
		} catch (IOException e) {
			System.out.println("io exception");
			apiKey="";
		}
		System.out.println("apikey = "+apiKey);
	}
	
	/***********************
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
	 ***********************/
	@Override
	public List<User> getRideRecommendations(User user, int numRecommendations) {
		if (numRecommendations<1) {
			return null;
		}
		User tempUser=us.getUserById(user.getUserId());
		List<User> riderCandidates = us.getUserByRoleAndLocation(!(user.isDriver()), tempUser.getBatch().getBatchLocation());
		List<User> recommendations = new ArrayList<>();
		List<Double> recommendationsDistance=new ArrayList<>();
		int currentRecommendations=0;
		Double distance=0d;
		List<User> apiUsers = new ArrayList<>();
		List<Double> apiDist = new ArrayList<>();
		boolean added=false;
		for(User u:riderCandidates) {
			distance=calculateKilometersBetweenPoints(user.getLatitude(), user.getLongitude(), u.getLatitude(), u.getLongitude());
			if(distance<maxSearchRadius) {
				if(distance>minSearchRadius) {
					apiDist.add(distance);
					apiUsers.add(u);
				}else {
					if(currentRecommendations==0) {
						recommendations.add(u);
						recommendationsDistance.add(distance);
						currentRecommendations++;
					}else {
						added=false;
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
						if (currentRecommendations>numRecommendations) {
							recommendations.remove(numRecommendations);
							recommendationsDistance.remove(numRecommendations);
							currentRecommendations--;
						}
					}
				}
			}
		}
		//at this point we have currentRecommendations / recommendaionsDistance filled with users that do not require api calls
		//and apiUsers / apiDist are filled with the users that qualify for api calls... so lets make that api call
		boolean apiCallSuccess=true;
		DistanceMatrix results=null;
		try {
			results = multipleUserDistance(user, apiUsers);
		} catch (Exception e) {
			apiCallSuccess=false;
		}
		if(results==null || !results.getStatus().equals("OK")) {
			apiCallSuccess=false;
		}
		// now lets merge those lists together
		int apiIndex=0;
		for (User u:apiUsers) {
			if(currentRecommendations==0) {
				recommendations.add(u);
				if(!apiCallSuccess || !results.getRows().get(0).getElements().get(0).getStatus().equals("OK")) {
					recommendationsDistance.add(apiDist.get(0));
				}else {
					recommendationsDistance.add(results.getRows().get(0).getElements().get(0).getDistance().getValue()/1000d);
				}
				currentRecommendations++;
				apiIndex++;
			}else {
				if(!apiCallSuccess || !results.getRows().get(0).getElements().get(apiIndex).getStatus().equals("OK")) {
					distance=apiDist.get(apiIndex);
				}else {
					distance=results.getRows().get(0).getElements().get(apiIndex).getDistance().getValue()/1000d;
				}
				apiIndex++;
				
				added=false;
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
				if (currentRecommendations>numRecommendations) {
					recommendations.remove(numRecommendations);
					recommendationsDistance.remove(numRecommendations);
					currentRecommendations--;
				}
			}
		}
		return recommendations;
	}
	
	
	/*********************
	 * 
	 * Gets DistanceMatrix object defining all the distances between user and every member of dUsers.
	 * 
	 * @param user -- origin for the distance calculation
	 * @param dUsers -- multiple possible destination users for user
	 * @return -- the whole object defining all of the distances and drive durations from user to the dUsers
	 * @throws Exception 
	 */
	public DistanceMatrix multipleUserDistance(User user, List<User>dUsers) throws Exception {
		if (dUsers.size()==0) {
			//nothing passed in so nothing returned.
			return null;	
		}
		boolean firstElement=true;
		String coordinates = "origins="+user.getLatitude()+","+user.getLongitude()+"&destinations=";
		for(User u:dUsers) {
			if(!firstElement) {
				coordinates+="|";
			}
			firstElement=false;
			coordinates+=u.getLatitude()+","+u.getLongitude();
		}
		try {
			return distanceMatrix.get(coordinates);
		} catch (GoogleApiException e) {
			throw e;
		}
	}
	
	/********************************
	 * 
	 *  calculateKilometersBetweenPoints returns the straight line distance between a pair of lat, long coordinates.
	 *  The formula takes into account the narrowing of a degree longitude as latitude approaches +-90 degrees.
	 * 
	 *  @lat1 -- latitude of first coordinate
	 *  @long1 -- longitude of first coordinate
	 *  @lat2 -- latitude of second coordinate
	 *  @long2 -- longitude of second coordinate
	 *  @returns -- straight line distance between provided coordinates
	 * 
	 *  Modification History:
	 *  
	 *  	Written: 10Feb2020
	 * 
	 ********************************/
	@Override
	public double calculateKilometersBetweenPoints(double lat1, double long1, double lat2, double long2) {
		final double radiusOfEarthKm=6378d;
		final double kmPerDegreeLat=111d;
		double kmPerDegreeLong = (Math.PI/180d)*radiusOfEarthKm*Math.cos(lat1*Math.PI/180d);
		double kmLatDistance=(lat1-lat2)*kmPerDegreeLat;
		double kmLongDistance=(long1-long2)*kmPerDegreeLong;
		return Math.sqrt(Math.pow(kmLatDistance,2d)+Math.pow(kmLongDistance,2d));
	}
	
	
	/****************************
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
	 *  
	 *  Modification History:
	 *  
	 *  	Written: 11Feb2020
	 * 
	 ***************************/
	private LoadingCache<String, DistanceMatrix> distanceMatrix = CacheBuilder.newBuilder()
			  .maximumSize(2000)
			  .expireAfterAccess(720, TimeUnit.HOURS)
			  .build(new CacheLoader<String, DistanceMatrix>() {
	    @Override
	    public DistanceMatrix load(String coordinates) throws Exception {
	    	getApiKey();
	    	if(apiKey.equals("")) {
	    		return null;
	    	}
	    	String DISTANCE_MATRIX_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&"
	  			  + coordinates+"&key="+apiKey;
	    	DistanceMatrix response = restTemplate.getForObject(DISTANCE_MATRIX_URL, DistanceMatrix.class);
	    	if (response != null) {
	    		return response;
	    	} else {
	    		throw new GoogleApiException("Google API Exception: Unable to find a route for coordinates: " + coordinates);
	    	}
	    }
	});

	/**********************************
	 * 
	 * 	Function returns road distance between two sets of lat/long coordinates in kilometers.
	 * 	Uses google maps api through use of distanceMatrix.get()
	 * 	Conversion of the two sets of lat/long into a string is to fit into the key/value pairing of the cache used by the function.
	 * 
	 *  @lat1 -- latitude of first coordinate
	 *  @long1 -- longitude of first coordinate
	 *  @lat2 -- latitude of second coordinate
	 *  @long2 -- longitude of second coordinate
	 *  @returns -- road distance value in kilometers between two coordinates
	 * 
	 *  Modification History:
	 *  
	 *  	Written: 11Feb2020 
	 * 
	 **********************************/
	@Override
	public double roadKilometersBetweenPoints(double lat1, double long1, double lat2, double long2) throws Exception{
		String coordinates = "origins="+lat1+","+long1+"&destinations="+lat2+","+long2;
		try {
		    return distanceMatrix.get(coordinates).getRows().get(0).getElements().get(0).getDistance().getValue()/1000d;
		  } catch (GoogleApiException e) {
		    throw e;
		  }
	}
}
