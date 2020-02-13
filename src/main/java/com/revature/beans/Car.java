package com.revature.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModelProperty;

/**
 * Car class that represents a user's car. All cars have an id, color, seats, make, model, year
 * and the corresponding user.
 * 
 * @author Adonis Cabreja
 *
 */

@Component
@Entity
@Table(name="cars")
public class Car implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="car_id")
	private int carId;
	
	@NotBlank(message="Car must have color")
	@Size(min = 2, max = 20, message="Color must be between 2 to 20 characters")
	@Pattern(regexp="[a-zA-Z\\s\\-]+", message="Color can only have letters and/or hyphens")
	@ApiModelProperty(allowableValues="range[2,20]", value="Color containing 1-20 letters, hyphens and spaces")
	private String color;
	
	@Min(value=1, message="Car cant have fewer than one seat")
	@Max(value=6, message="Car cant have more than six seats")
	@ApiModelProperty(allowableValues="range[1,6]", value="Available seats in a car")
	private int seats;
	
	@NotBlank(message="Car must have make")
	@Pattern(regexp="[0-9a-zA-Z\\s\\-]+", message="Make can only have letters, numbers and/or hyphens")
	@ApiModelProperty(value="Car make containing letters, hyphens and spaces")
	private String make;
	
	@NotBlank(message="Car must have model")
	@Pattern(regexp="[0-9a-zA-Z\\s\\-]+", message="Model can only have letters, numbers and/or hyphens")
	@ApiModelProperty(value="Car model containing letters, hyphens and spaces")
	private String model;
	
	@Column(name="car_year")
	@Min(value=1970, message="Car must be from 1970 or later")
	@ApiModelProperty(value="Car year that must be after 1970")
	private int year;
	
	@OneToOne
	@JoinColumn(name="user_id", unique=true)
	@NotNull(message="Car must have associated driver")
	@ApiModelProperty(value="Driver of the car")
	private User user;
	
	public Car() {
		super();
	}

	public Car(int carId, String color, int seats, String make, String model, int year, User user) {
		super();
		this.carId = carId;
		this.color = color;
		this.seats = seats;
		this.make = make;
		this.model = model;
		this.year = year;
		this.user = user;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + carId;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((make == null) ? 0 : make.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + seats;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (carId != other.carId)
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} 
		else if (!color.equals(other.color))
			return false;
		if (make == null) {
			if (other.make != null)
				return false;
		} 
		else if (!make.equals(other.make))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} 
		else if (!model.equals(other.model))
			return false;
		if (seats != other.seats)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} 
		else if (!user.equals(other.user))
			return false;
		return year == other.year;
	}

	@Override
	public String toString() {
		return "Car [carId=" + carId + ", color=" + color + ", seats=" + seats + ", make=" + make + ", model=" + model
				+ ", year=" + year + ", user=" + user + "]";
	}
	
}

