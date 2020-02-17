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
	public void setDemo() {
		batch = new Batch(123, "location");
		user = new User(1, "userName", batch, "John", "Smith", "email@email.com", "1231231234", "123 Main St", 1.0, 1.0);
		car = new Car(1, "white", 5, "Jeep", "Compass", 2019, user);
		comparedToCar = new Car(1, "white", 5, "Jeep", "Compass", 2019, user);
	}

	@Test
	public void testToString() {
		String actual = car.toString();
		String expected = "Car [carId=" + car.getCarId() + ", color=" + car.getColor() 
				+ ", seats=" + car.getSeats() + ", make=" + car.getMake() + ", model=" 
				+ car.getModel() + ", year=" + car.getYear() + ", user=" + user + "]";
		assertEquals(expected, actual);
	}
	
	@Test
	public void testHashCode() {
		assertTrue(car.hashCode() == comparedToCar.hashCode());
		assertNotNull(car.hashCode());
		car.setColor(null);
		car.setMake(null);
		car.setModel(null);
		car.setUser(null);
		assertNotNull(car.hashCode());
	}
	
	@Test
	public void testEqual() {
		assertTrue(car.equals(comparedToCar) && comparedToCar.equals(car));
	}
	
	@Test
	public void testEqualsNull() {
		comparedToCar = null;
		assertFalse(car.equals(comparedToCar));
	}
	
	@Test
	public void testEqualsGetClass() {
		assertFalse(car.equals(user));
	}
	
	@Test
	public void testEqualsColorNull() {
		car.setColor(null);
		assertFalse(car.equals(comparedToCar));
	}
	
	@Test
	public void testEqualsColorNotTheSameAsCar() {
		car.setColor("newColor");
		assertFalse(car.equals(comparedToCar));
	}
	
	@Test
	public void testEqualsMakeNull() {
		car.setMake(null);
		assertFalse(car.equals(comparedToCar));
	}
	
	@Test
	public void testEqualsMakeNotTheSameAsCar() {
		car.setMake("newMake");
		assertFalse(car.equals(comparedToCar));
	}

	@Test
	public void testEqualsModelNull() {
		car.setModel(null);
		assertFalse(car.equals(comparedToCar));
	}
	
	@Test
	public void testEqualsModelNotTheSameAsCar() {
		car.setModel("newModel");
		assertFalse(car.equals(comparedToCar));
	}
	
	@Test
	public void testEqualsSeatNotTheSameAsCar() {
		car.setSeats(2);
		assertFalse(car.equals(comparedToCar));
	}
	
	@Test
	public void testEqualsUserNull() {
		car.setUser(null);
		assertFalse(car.equals(comparedToCar));
	}
	
	@Test
	public void testEqualsUserNotTheSameAsCar() {
		User otherUser = new User(2, "userName", batch, "John", "Smith", "email@email.com", "1231231234", "123 Main St", 1.0, 1.0);
		car.setUser(otherUser);
		car.setYear(2019);
		assertFalse(car.equals(comparedToCar));
	}
}
