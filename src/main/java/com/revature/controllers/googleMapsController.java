package com.revature.controllers;

import java.util.Arrays;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;


@RestController
@CrossOrigin
public class googleMapsController {
	
	
	
	@GetMapping( "/points/{origin}/{destination}" )
	public String getPoints( @PathVariable("origin") String origin, @PathVariable("destination") String destination ) {

		String apiKey = "";
		
		RestTemplate restTemplate = new RestTemplate();
		
//		System.out.println( origin + " " +  destination );
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity <String> entity = new HttpEntity<String>(headers);
		String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination=" + destination + "&key=" + apiKey;
		
		JsonParser parser = new JsonParser();
		JsonElement jsonTree = parser.parse( restTemplate.exchange( url, HttpMethod.GET, entity, String.class ).getBody() );
		JsonObject jsonObject = jsonTree.getAsJsonObject();
		JsonArray jarray = jsonObject.get( "routes" ).getAsJsonArray();
		jsonObject = jarray.get( 0 ).getAsJsonObject();
		JsonElement jsonElement = jsonObject.get( "overview_polyline" );
		jsonObject = jsonElement.getAsJsonObject();
		String points = jsonObject.get( "points" ).toString();
		
		return points;	
	}
	
}
