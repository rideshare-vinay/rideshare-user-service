package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.beans.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

}
