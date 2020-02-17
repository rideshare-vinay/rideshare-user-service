package com.revature.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserTest {

	@Test
	public void testHashCode() {
		User u = new User(1, "userName", null, "John", "Smith", "email@email.com", "1231231234", "123 Main St", 1.0, 1.0);
		assertNotNull(u.hashCode());
		
		Batch b=new Batch();
		User u2 = new User(1, null, b, null, null, null, null, null,0.0, 0.0);
		u2.setDriver(true);
		u2.setAcceptingRides(true);
		u2.setActive(true);
		assertNotNull(u2.hashCode());
	}
	
	@Test
	public void testNotEquals() {
		User u = new User(0, "user", null, "John", "Doe", "email@email.com", "1234567890", "123 Main St", 1.0, 1.0);
		Batch b=new Batch();
		User u2 = new User(1, null, b, null, null, null, null, null, 1.0, 1.0);
		assertFalse(u.equals(u2));
		assertFalse(u.equals(null));
		assertFalse(u.equals(b));
		
		Batch b2 = new Batch();
		b2.setBatchLocation("test location");
		u = new User(0, "user", b2, "John", "Doe", "email@email.com", "1234567890", "123 Main St", 1.0, 1.0);
		u2 = new User(1, "user", b, "John", "Doe", "email@email.com", "1234567890", "123 Main St", 1.0, 1.0);
		assertFalse(u.equals(u2));
		
		u = new User(0, "user", b2, "John", "Doe", "email@email.com", "1234567890", "123 Main St", 1.0, 1.0);
		u2 = new User(0, "user", b2, "John", "Doe", "email@email.com", "1234567890", "123 Main St", 1.0, 1.0);
		u.setEmail(null);
		assertFalse(u.equals(u2));
		
		u.setEmail("email@email.com");
		u.setFirstName(null);
		assertFalse(u.equals(u2));
		
		u2.setFirstName("Mary");
		assertFalse(u.equals(u2));
		
		u2.setFirstName(u.getFirstName());
		u2.setAcceptingRides(true);
		assertFalse(u.equals(u2));

		u2.setAcceptingRides(false);
		u2.setActive(true);
		assertFalse(u.equals(u2));

		u2.setActive(false);
		u2.setDriver(true);
		assertFalse(u.equals(u2));

		u2.setDriver(false);
		u.setLastName(null);
		assertFalse(u.equals(u2));

		u.setLastName("Sue");
		assertFalse(u.equals(u2));

		u.setLastName("Doe");
		u.setPhoneNumber(null);
		assertFalse(u.equals(u2));

		u.setPhoneNumber("1234567891");
		assertFalse(u.equals(u2));

		u.setPhoneNumber("1234567890");
		u.setUserName(null);
		assertFalse(u.equals(u2));

		u.setUserName("user2");
		assertFalse(u.equals(u2));
	}

	@Test
	public void testToString() {
		User u = new User(0, "user", null, "John", "Doe", "email@email.com", "1234567890", "123 Main St", 1.0, 1.0);
		assertEquals(u.toString(), "User [userId=" + u.getUserId() + ", userName=" + u.getUserName() + ", batch=" + u.getBatch() + ", firstName=" 
		+ u.getFirstName() + ", lastName=" + u.getLastName() + ", email=" + u.getEmail() + ", phoneNumber=" + u.getPhoneNumber() + ", isDriver=" 
				+ false + ", isActive=" + false + ", isAcceptingRides=" + false + "]");
	}
	
}
