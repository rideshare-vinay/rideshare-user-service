package com.revature.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Car;
import com.revature.repositories.CarRepository;
import com.revature.services.CarService;

@Service
public class CarServiceImpl implements CarService {
	
	@Autowired
	private CarRepository cr;
	
	@Override
	public List<Car> getCars() {
		return cr.findAll();
	}

	@Override
	public Car getCarById(int id) {
		return cr.getOne(id);
	}

	@Override
	public Car getCarByUserId(int userId) {
		return cr.getCarByUserId(userId);
	}
	@Override
	public Car addCar(Car car) {
		return cr.save(car);
	}

	@Override
	public Car updateCar(Car car) {
		return cr.save(car);
	}

	@Override
	public String deleteCarById(int id) {
		cr.deleteById(id);
		return "Car with id: " + id + " was deleted.";
	}

}
