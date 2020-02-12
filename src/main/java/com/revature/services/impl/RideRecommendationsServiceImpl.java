package com.revature.services.impl;

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
import com.revature.services.BatchService;
import com.revature.services.RideRecommendationsService;
import com.revature.services.UserService;

@Service
public class RideRecommendationsServiceImpl implements RideRecommendationsService {
	// minSearchRadius is the minimum distance between two users (in km) for which we will actually hit the google maps api.
	// anything closer and there is simply no need.  The effective distance will be treated as 0 for recommendations.
	private final double minSearchRadius=0.2d;
	// maxSearchRadius is the maximum distance between riders where we will consider a recommendation.
	private final double maxSearchRadius=20.0d;
	
	//private static final String DISTANCE_MATRIX_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&"
			  //+ "{coordinates}&key={apiKey}";
			 
			
	private String apiKey = "get your own dang key";
	private RestTemplate restTemplate = new RestTemplate();
	
	
	@Autowired
	private UserService us;
	
	@Autowired
	private BatchService bs;
	
	
	/***********************
	 *  
	 *  getRideRecommendations returns a maximum number of recommendations equal to or less than numRecommendations 
	 *  that live closest to user (if there are not enough candidates that live close enough, fewer are returned).
	 *  If user is a driver, function returns candidates that are not drivers.
	 *  If user is not a driver, function returns drivers.
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
		List<User> riderCandidates = us.getUserByRoleAndLocation(!(user.isDriver()), user.getBatch().getBatchLocation());
		List<User> recommendations = new ArrayList<>();
		List<Double> recommendationsDistance=new ArrayList<>();
		int currentRecommendations=0;
		Double distance=0d;
		Double saveDistance=0d;
		for(User u:riderCandidates) {
			distance=calculateKilometersBetweenPoints(user.getLatitude(), user.getLongitude(), u.getLatitude(), u.getLongitude());
			if(distance<maxSearchRadius) {
				if(distance>minSearchRadius) {
					try {
						saveDistance=distance;
						distance=roadKilometersBetweenPoints(user.getLatitude(), user.getLongitude(), u.getLatitude(), u.getLongitude());
					} catch (Exception e) {
						//google api call failed so we just go with our previous calculation
						distance=saveDistance;
					}
				}
				if(currentRecommendations==0) {
					recommendations.add(u);
					recommendationsDistance.add(distance);
					currentRecommendations++;
				}else {
					for(int i=0; i<currentRecommendations; i++) {
						if(distance<recommendationsDistance.get(i)) {
							recommendations.add(i,u);
							recommendationsDistance.add(i,distance);
							currentRecommendations++;
							break;
						}
					}
					if (currentRecommendations>numRecommendations) {
						recommendations.remove(numRecommendations);
						recommendationsDistance.remove(numRecommendations);
						currentRecommendations--;
					}
				}
			}
		}
		return recommendations;
	}
	
	
	/********************************
	 * 
	 *  calculateKilometersBetweenPoints returns the straight line distance between a pair of lat, long coordinates.
	 *  The formula takes into account the narrowing of a degree longitude as latitude approaches +-90 degrees.
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
	      String DISTANCE_MATRIX_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&"
	  			  + coordinates+"&key="+apiKey;
	      DistanceMatrix response = restTemplate.getForObject(DISTANCE_MATRIX_URL, DistanceMatrix.class);
	      System.out.println(response);
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
