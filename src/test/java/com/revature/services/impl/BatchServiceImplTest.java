package com.revature.services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.beans.Batch;
import com.revature.repositories.BatchRepository;

@RunWith(SpringRunner.class)
public class BatchServiceImplTest {
	
	@InjectMocks
	private BatchServiceImpl bsi;
	
	@Mock
	private BatchRepository br;
	
	@Test
	public void testGettingBatches() {
		
		List<Batch> batches = new ArrayList<>();
		batches.add(new Batch());
		batches.add(new Batch());
		when(br.findAll()).thenReturn(batches);
		
		assertEquals(bsi.getBatches().size(), 2);
	}
	
	@Test
	public void testGettingBatchByNumber() {
		
		Batch expected = new Batch(123, "location");
		when(br.getOne(123)).thenReturn(expected);
		Batch actual = bsi.getBatchByNumber(123);
		
		assertEquals(actual, expected);
	}
	
	@Test
	public void testGettingBatchesByLocation() {
		
		List<Batch> batches = new ArrayList<>();
		batches.add(new Batch(123, "location"));
		batches.add(new Batch(456, "location"));
		when(br.getBatchByLocation("location")).thenReturn(batches);
		
		assertEquals(bsi.getBatchByLocation("location").size(), 2);
	}
	
	@Test
	public void testAddingBatch() {
		
		Batch expected = new Batch(123, "location");
		when(br.save(expected)).thenReturn(expected);
		Batch actual = bsi.addBatch(expected);
		
		assertEquals(actual, expected);
	}
	
	@Test
	public void testUpdatingBatch() {
		
		Batch expected = new Batch(123, "location");
		when(br.save(expected)).thenReturn(expected);
		Batch actual = bsi.updateBatch(expected);
		
		assertEquals(actual, expected);
	}
	
	@Test
	public void testDeletingBatch() {
		
		String expected = "Batch number: 123 was deleted.";
		when(br.existsById(123)).thenReturn(true);
		String actual = bsi.deleteBatchByNumber(123);
		
		assertEquals(actual, expected);
	}
}
