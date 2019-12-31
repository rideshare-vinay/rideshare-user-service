package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.beans.Batch;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {

}
