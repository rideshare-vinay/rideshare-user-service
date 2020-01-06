package com.revature.services;

import java.util.List;

import com.revature.beans.Batch;

public interface BatchService {
	
	public List<Batch> getBatches();
	public Batch getBatchByNumber(int number);
	public List<Batch> getBatchByLocation(String location);
	public Batch addBatch(Batch batch);
	public Batch updateBatch(Batch batch);
	public String deleteBatchByNumber(int number);
}
