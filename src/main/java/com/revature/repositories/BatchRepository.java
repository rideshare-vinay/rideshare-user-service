package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.beans.Batch;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {
	
	@Query("select b from Batch b where b.batchLocation = ?1")
	public List<Batch> getBatchByLocation(String location);
	
	@Query("select b from Batch b where b.batchLocation = ?1 and b.batchNumber = ?2")
	public List<Batch> getBatchByLocationAndNumber(String location, int number);
}
