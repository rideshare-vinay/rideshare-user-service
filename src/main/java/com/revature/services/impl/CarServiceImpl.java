package com.revature.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.revature.beans.Car;
import com.revature.repositories.CarRepository;
import com.revature.services.CarService;

/**
 * CarServiceImpl handles any additional services that need to be made before calling the
 * repository methods.
 * 
 * @author Adonis Cabreja
 *
 */

@Service
public class CarServiceImpl implements CarService {
	
	@Autowired
	private CarRepository cr;
	
	/**
	 * Calls CarRepository's findAll method found in the JpaRepository.
	 * 
	 * @return A list of all the cars.
	 */
	
	@Override
	public List<Car> getCars() {
		return cr.findAll();
	}

	/**
	 * Calls CarRepository's getOne method found in the JpaRepository.
	 * 
	 * @param id represents the car's id.
	 * @return A car that matches the car's id.
	 */
	
	@Override
	public Car getCarById(int id) {
		return cr.getOne(id);
	}

	/**
	 * Calls CarRepository's custom query method getCarByUserId.
	 * 
	 * @param userId represents the user's id.
	 * @return A car that matches the user's id.
	 */
	
	@Override
	public Car getCarByUserId(int userId) {
		return cr.getCarByUserId(userId);
	}
	
	/**
	 * Calls CarRepository's save method found in the JpaRepository.
	 * 
	 * @param car represents the new Car object being sent.
	 * @return The newly created object.
	 */
	
	@Override
	public Car addCar(Car car) {
		if(validateCar(car)) {
		return cr.save(car);
		}
		else {
			return null;
		}
	}

	/**
	 * Calls CarRepository's save method found in the JpaRepository.
	 * 
	 * @param car represents the updated Car object being sent.
	 * @return The newly updated object.
	 */
	
	@Override
	public Car updateCar(Car car) {
		if(validateCar(car)) {
		return cr.save(car);
		}
		else {
			return null;
		}
	}

	private boolean validateCar(Car car) {	
		String make = car.getMake();
		boolean teslaOrLexus = make.equals("Tesla")||make.equals("Lexus");
		try {
			//get XML from API
			URL url=new URL("https://www.fueleconomy.gov/ws/rest/vehicle/menu/model?year="+car.getYear()+"&make="+car.getMake());
			InputStream stream = (url.openStream());
			StringBuilder xml= new StringBuilder("");
			while(stream.available()>0) {
				xml.append((char)stream.read());
			}
			String parsedXML=xml.toString();
			//parse values
			String[] models=parsedXML.split("<value>");
			if(models.length<2) {//no models for the year/make combination
				return false;
			}
			//extract value and filter dupes
			for(int i=1; i<models.length; i++) {
				models[i]=models[i].substring(0,models[i].indexOf("</value>"));
				models[i]=models[i].split(" \\wWD")[0];
				models[i]=models[i].split(" \\w-Door")[0];
				if(teslaOrLexus) {models[i]=models[i].split(" ")[0]+" "+models[i].split(" ")[1];}
				if(models[i].equals(car.getModel())){
					return true;
				}
			}
			
			
		}
		catch (IOException e) {

		}
		return false;
	}
	
	/**
	 * Calls CarRepository's deleteById method found in the JpaRepository.
	 * 
	 * @param id represents the car's id.
	 * @return A string that says which car was deleted.
	 */
	@Override
	public String deleteCarById(int id) {
		cr.deleteById(id);
		return "Car with id: " + id + " was deleted.";
	}

}
