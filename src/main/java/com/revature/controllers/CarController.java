package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Car;
import com.revature.services.CarService;

@RestController
@RequestMapping("/cars")
@CrossOrigin
public class CarController {
	
	@Autowired
	private CarService cs;
	
	@GetMapping
	public List<Car> getCars() {
		
		return cs.getCars();
	}
	
	@GetMapping("/{id}")
	public Car getCarById(@PathVariable("id")int id) {
		
		return cs.getCarById(id);
	}
	
	@PostMapping
	public ResponseEntity<Car> addCar(@RequestBody Car car) {
		
		return new ResponseEntity<>(cs.addCar(car), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public Car updateCar(@RequestBody Car car) {
		
		return cs.updateCar(car);
	}
	
	@DeleteMapping("/{id}")
	public String deleteCarById(@PathVariable("id")int id) {
		
		return cs.deleteCarById(id);
	}
}
