package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.beans.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
	
	@Query("select c from Car c where c.user.userId = ?1")
	public Car getCarByUserId(int userId);
}
