package com.revature.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.beans.Car;
import com.revature.beans.User;
import com.revature.repositories.CarRepository;

@RunWith(SpringRunner.class)
public class CarServiceImplTest {
	
	@InjectMocks
	private CarServiceImpl csi;
	
	@Mock
	private CarRepository cr;
	
	@Test
	public void testGettingCars() {
		
		List<Car> cars = new ArrayList<>();
		cars.add(new Car());
		cars.add(new Car());
		when(cr.findAll()).thenReturn(cars);
		
		assertEquals(2, csi.getCars().size());
	}
	
	@Test
	public void testGettingCarById() {
		
		Car expected = new Car(1, "red", 4, "Honda", "Accord", 2015, new User());
		when(cr.getOne(1)).thenReturn(expected);
		Car actual = csi.getCarById(1);
		
		assertEquals(actual, expected);
	}
	
	@Test
	public void testGettingCarByUserId() {
		
		Car expected = new Car(1, "red", 4, "Honda", "Accord", 2015, new User());
		when(cr.getCarByUserId(1)).thenReturn(expected);
		Car actual = csi.getCarByUserId(1);
		
		assertEquals(actual, expected);
	}
	
	@Test
	public void testAddingCar() {
		
		Car expected = new Car(1, "red", 4, "Honda", "Accord", 2015, new User());
		when(cr.save(expected)).thenReturn(expected);
		Car actual = csi.addCar(expected);
		
		assertEquals(actual, expected);
	}
	
	@Test
	public void testUpdatingCar() {
		
		Car expected = new Car(1, "red", 4, "Honda", "Accord", 2015, new User());
		when(cr.save(expected)).thenReturn(expected);
		Car actual = csi.updateCar(expected);
		
		assertEquals(actual, expected);
	}
	
	@Test
	public void testDeletingCar() {
		
		String expected = "Car with id: 1 was deleted.";
		when(cr.existsById(1)).thenReturn(true);
		String actual = csi.deleteCarById(1);
		
		assertEquals(actual, expected);
	}
	
	@Test
	public void testAddCarNull() {
		Car car = new Car(1, "red", 4, "testCompany", "testModel", 2015, new User());
		assertNull(csi.addCar(car));
	}
	
	@Test
	public void testUpdateCarNull() {
		Car car = new Car(1, "red", 4, "testCompany", "testModel", 2015, new User());
		assertNull(csi.updateCar(car));
	}
}
