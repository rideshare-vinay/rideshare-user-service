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

import com.revature.beans.Batch;
import com.revature.services.BatchService;

@RestController
@RequestMapping("/batches")
@CrossOrigin
public class BatchController {
	
	@Autowired
	private BatchService bs;
	
	@GetMapping
	public List<Batch> getBatches() {
		
		return bs.getBatches();
	}
	
	@GetMapping("/{number}")
	public Batch getBatchByNumber(@PathVariable("number")int number) {
		
		return bs.getBatchByNumber(number);
	}
	
	@PostMapping
	public ResponseEntity<Batch> addBatch(@RequestBody Batch batch) {
		
		return new ResponseEntity<>(bs.addBatch(batch), HttpStatus.CREATED);
	}
	
	@PutMapping("/{number}")
	public Batch updateBatch(@RequestBody Batch batch) {
		
		return bs.updateBatch(batch);
	}
	
	@DeleteMapping("/{number}")
	public String deleteBatchByNumber(@PathVariable("number")int number) {
		
		return bs.deleteBatchByNumber(number);
	}
}
