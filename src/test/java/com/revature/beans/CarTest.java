package com.revature.beans;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CarTest {

	private Car car;
	private Car comparedToCar;
	private User user;
	private Batch batch;;

	@Before
	public void setNewCar() {
		batch = new Batch(123, "location");
		user = new User(1, "userName", batch, "adonis", "cabreja", "adonis@gmail.com", "123-456-7890");
		car = new Car(1, "white", 5, "Jeep", "Compass", 2019, user);
		comparedToCar = new Car(1, "white", 5, "Jeep", "Compass", 2019, user);
	}

	@Test
	public void testToString() {
		String actual = car.toString();
		String expected = "Car [carId=1, color=white, seats=5, make=Jeep, model=Compass, year=2019, user=User [userId=1, userName=userName, batch=Batch [batchNumber=123, batchLocation=location], firstName=adonis, lastName=cabreja, email=adonis@gmail.com, phoneNumber=123-456-7890, isDriver=false, isActive=false, isAcceptingRides=false]]";
		assertEquals(expected, actual);
	}
	
	@Test
	public void testHashCode() {
		assertTrue(car.hashCode() == comparedToCar.hashCode());
	}
	
	@Test
	public void testEqual() {
		assertTrue(car.equals(comparedToCar) && comparedToCar.equals(car));
	}
	
	@Test
	public void testEqualsNull() {
		comparedToCar = new Car();
		assertFalse(car.equals(comparedToCar));
	}
	
	@Test
	public void testEqualsGetClass() {
		assertFalse(car.equals(user));
	}
	
//	@Test
//	public void testEqualsCarNull() {
//		Car other = null;
//		assertFalse(car.equals(other));
//	}

}
