package com.revature.controllers;

import java.util.List;

import javax.validation.Valid;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cars")
@CrossOrigin
@Api(tags= {"Car"})
public class CarController {
	
	@Autowired
	private CarService cs;
	
	@ApiOperation(value="Returns all cars", tags= {"Car"})
	@GetMapping
	public List<Car> getCars() {
		
		return cs.getCars();
	}
	
	@ApiOperation(value="Returns car by id", tags= {"Car"})
	@GetMapping("/{id}")
	public Car getCarById(@PathVariable("id")int id) {
		
		return cs.getCarById(id);
	}
	
	@ApiOperation(value="Adds a new car", tags= {"Car"})
	@PostMapping
	public ResponseEntity<Car> addCar(@Valid @RequestBody Car car) {
		
		return new ResponseEntity<>(cs.addCar(car), HttpStatus.CREATED);
	}
	
	@ApiOperation(value="Updates car by id", tags= {"Car"})
	@PutMapping("/{id}")
	public Car updateCar(@Valid @RequestBody Car car) {
		
		return cs.updateCar(car);
	}
	
	@ApiOperation(value="Deletes car by id", tags= {"Car"})
	@DeleteMapping("/{id}")
	public String deleteCarById(@PathVariable("id")int id) {
		
		return cs.deleteCarById(id);
	}
}
