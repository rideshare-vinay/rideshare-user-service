package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.User;
import com.revature.services.RideRecommendationsService;


@RestController
@CrossOrigin
public class GoogleMapsController {

	@Autowired
	private RideRecommendationsService rrs;
	
	
	/****************************
	 * 
	 * This function is just the end point to make calls to our recommendation algorithm from the front end.
	 * 
	 * @param user -- user for whom ride recommendations are being made
	 * @param num -- how many recommendations do we want returned
	 * @return -- list of drivers or riders returned by recommendation
	 ****************************/
	@PostMapping("/recommendations/{num}")
	public List<User> getRecommendations(@RequestBody User user, @PathVariable("num") int num){
		return rrs.getRideRecommendations(user, num);
	}


}
