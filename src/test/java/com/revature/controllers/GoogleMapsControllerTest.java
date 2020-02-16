package com.revature.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Batch;
import com.revature.beans.User;
import com.revature.services.RideRecommendationsService;

@RunWith(SpringRunner.class)
@WebMvcTest(GoogleMapsController.class)
public class GoogleMapsControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper om;
	
	@MockBean
	private RideRecommendationsService rrs;
	
	private User mockUser;
	
	@Before
	public void init() {
		mockUser = new User();
		mockUser.setUserId(1);
		mockUser.setUserName("userName");
		mockUser.setFirstName("John");
		mockUser.setLastName("Smith");
		mockUser.setActive(true);
		mockUser.setAcceptingRides(false);
		mockUser.setDriver(false);
		mockUser.setAddress("UTA - Arlington, TX");
		mockUser.setBatch(new Batch(1912, "UTA - Arlington, TX"));
		mockUser.setEmail("email@email.com");
		mockUser.setLatitude(32.727219);
		mockUser.setLongitude(-97.105942);
	}
	
	
	@Test
	public void test() throws Exception {
		List<User> mockUserList = new ArrayList<>();
		mockUserList.add(mockUser);
		when(rrs.getRideRecommendations(mockUser, 5)).thenReturn(mockUserList);
		
		mvc.perform(post("/recommendations/{num}", 5).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(mockUser)))
			.andExpect(status().isOk());
			
	}

}
