package com.revature.services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.beans.Batch;
import com.revature.beans.User;

@RunWith(SpringRunner.class)
public class RideRecommendationsServiceImplTest {
	
	@InjectMocks
	private RideRecommendationsServiceImpl rideService;
	
	@Mock
	private UserServiceImpl userService; 
	
	
	@Test
	public void testGetRideRecommendations() {
		User mockUser = new User();
		mockUser.setAcceptingRides(false);
		mockUser.setActive(true);
		mockUser.setAddress("123 Main St");
		mockUser.setBatch(new Batch(123, "UTA - Arlington, TX"));
		mockUser.setDriver(false);
		mockUser.setEmail("email@email.com");
		mockUser.setFirstName("John");
		mockUser.setLastName("Smith");
		mockUser.setLatitude(100);
		mockUser.setLongitude(100);
		mockUser.setPhoneNumber("1231231234");
		mockUser.setUserName("JohnSmith25");
		
		List<User> emptyRecs = rideService.getRideRecommendations(mockUser, 0);
		
		assertEquals(emptyRecs.size(), 0);
	
		
		
	}

}
