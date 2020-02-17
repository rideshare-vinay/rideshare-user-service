package com.revature.services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
	
	
	private User mockUser;
	
	@org.junit.Before
	public void init() {
		mockUser = new User();
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
	}
	
	@Test
	public void testGetRideRecommendationsWithNoRecs() {
		List<User> emptyRecs = rideService.getRideRecommendations(mockUser, 0);
		
		assertEquals(emptyRecs.size(), 0);
	}
	
	@Test
	public void testgetRideRecommendationsWithRecs() {
		List<User> expectedUsers = new ArrayList<>();
		expectedUsers.add(mockUser);
		
		when(userService.getUserById(mockUser.getUserId())).thenReturn(mockUser);
		when(
				userService.getUserByRoleAndLocation(
						!mockUser.isDriver(), 
						mockUser.getBatch().getBatchLocation()))
		.thenReturn(expectedUsers);
		
		List<User> recs = rideService.getRideRecommendations(mockUser, 5);
		assertEquals(recs.size(), 0);
	}
	
	@Test
	public void testRoadKilometersBetweenPoints() {
		double lat1 = 32.727220;
		double long1 = -97.105940;
		
		double lat2 = 32.736050;
		double long2 = -97.113750;
		
		double calculatedDistance = 0.0;
		try {
			calculatedDistance = rideService.roadKilometersBetweenPoints(lat1, long1, lat2, long2);
		} catch (Exception e) {
			
		}
		assertEquals(calculatedDistance, 2.535, 1);
	}
	
	@Test
	public void testcalculateKilometersBetweenPoints() {
		double lat1 = 32.727220;
		double long1 = -97.105940;
		
		double lat2 = 32.736050;
		double long2 = -97.113750;
		
		double expectedDistance = 1.609344;
		
		double distance = rideService.calculateKilometersBetweenPoints(lat1, long1, lat2, long2);
		assertEquals(expectedDistance, distance, 0.4);
	}
	

}
