package com.revature.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Batch;
import com.revature.repositories.BatchRepository;
import com.revature.services.BatchService;

@Service
public class BatchServiceImpl implements BatchService {
	
	@Autowired
	private BatchRepository br;
	
	@Override
	public List<Batch> getBatches() {
		return br.findAll();
	}

	@Override
	public Batch getBatchByNumber(int number) {
		return br.getOne(number);
	}

	@Override
	public Batch addBatch(Batch batch) {
		return br.save(batch);
	}

	@Override
	public Batch updateBatch(Batch batch) {
		return br.save(batch);
	}

	@Override
	public String deleteBatchByNumber(int number) {
		br.deleteById(number);
		return "Batch number: " + number + " was deleted.";
	}

}
