package com.revature.controllers;

import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.revature.beans.googlemaps.PlaceDetails;
import com.revature.beans.googlemaps.PlaceDetailsResponse;
import com.revature.beans.googlemaps.PlaceLocation;
import com.revature.exceptions.PlacesException;


@RestController
@CrossOrigin
public class CachingExample {

	private static final String PLACE_DETAILS_URL = "https://maps.googleapis.com/"
			  + "maps/api/place/details/json?reference="
			  + "{searchId}&sensor=false&key={key}";
			 
			
	private String apiKey = "get your own key";
	//private String placeId = "ChIJa147K9HX3IAR-lwiGIQv9i4";
	private RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping("/test/{id}")
	public PlaceLocation test(@PathVariable("id") String placeId) {
		System.out.println("place = "+placeId);
		System.out.println("key = "+this.apiKey);
		System.out.println(PLACE_DETAILS_URL);
		
		try {
			PlaceDetails result;
			result = getPlaceDetails(placeId);
			return result.getGeometry().getLocation();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		 
		
	}
	
	private LoadingCache<String, PlaceDetails> placeDetails = CacheBuilder.newBuilder()
			  .maximumSize(1000)
			  .expireAfterAccess(24, TimeUnit.HOURS)
			  .build(new CacheLoader<String, PlaceDetails>() {
	    @Override
	    public PlaceDetails load(String searchId) throws Exception {
	      PlaceDetailsResponse response = restTemplate.getForObject(PLACE_DETAILS_URL, PlaceDetailsResponse.class, searchId, apiKey);
	      if (response.getResult() != null) {
	    	  return response.getResult();
	      } else {
	    	  throw new PlacesException("Unable to find details for reference: " + searchId);
	      }
	    }
	});
	
	public PlaceDetails getPlaceDetails(String searchId) throws Exception{
		  try {
		    return placeDetails.get(searchId);
		  } catch (PlacesException e) {
		    throw e;
		  }
		}
	
}


