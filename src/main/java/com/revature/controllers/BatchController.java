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

import com.revature.beans.Batch;
import com.revature.services.BatchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/batches")
@CrossOrigin
@Api(tags= {"Batch"})
public class BatchController {
	
	@Autowired
	private BatchService bs;
	
	@ApiOperation(value="Returns all batches", tags= {"Batch"})
	@GetMapping
	public List<Batch> getBatches() {
		
		return bs.getBatches();
	}
	
	@ApiOperation(value="Returns batch by id", tags= {"Batch"})
	@GetMapping("/{number}")
	public Batch getBatchByNumber(@PathVariable("number")int number) {
		
		return bs.getBatchByNumber(number);
	}
	
	@ApiOperation(value="Adds a new batch", tags= {"Batch"})
	@PostMapping
	public ResponseEntity<Batch> addBatch(@Valid @RequestBody Batch batch) {
		
		return new ResponseEntity<>(bs.addBatch(batch), HttpStatus.CREATED);
	}
	
	@ApiOperation(value="Updates batch by id", tags= {"Batch"})
	@PutMapping("/{number}")
	public Batch updateBatch(@Valid @RequestBody Batch batch) {
		
		return bs.updateBatch(batch);
	}
	
	@ApiOperation(value="Deletes batch by id", tags= {"Batch"})
	@DeleteMapping("/{number}")
	public String deleteBatchByNumber(@PathVariable("number")int number) {
		
		return bs.deleteBatchByNumber(number);
	}
}
