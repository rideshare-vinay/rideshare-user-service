package com.revature.beans;

import static org.junit.Assert.*;

import org.junit.Test;

public class BatchTest {

	@Test
	public void testHashCode() {
		Batch b = new Batch(1, "testbatch");
		assertNotNull(b.hashCode());
		b.setBatchLocation(null);
		assertNotNull(b.hashCode());
	}
	
	@Test
	public void testNotEquals() {
		Batch b = new Batch(1, "testbatch");
		Batch b2 = null;
		assertFalse(b.equals(b2));

		User u = new User();
		assertFalse(b.equals(u));
		
		b2 = new Batch(1, "testbatch");
		b.setBatchLocation(null);
		assertFalse(b.equals(b2));
		
		b.setBatchLocation(b2.getBatchLocation());
		b.setBatchNumber(2);
		assertFalse(b.equals(b2));
	}
	
	@Test
	public void testToString() {
		Batch b = new Batch(1, "testbatch");
		assertEquals(b.toString(),"Batch [batchNumber=" + b.getBatchNumber() 
		+ ", batchLocation=" + b.getBatchLocation() + "]");
	}
	

}
