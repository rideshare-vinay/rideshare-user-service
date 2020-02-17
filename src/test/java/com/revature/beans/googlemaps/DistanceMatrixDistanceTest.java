package com.revature.beans.googlemaps;

import static org.junit.Assert.*;

import org.junit.Test;

public class DistanceMatrixDistanceTest {

	@Test
	public void testConstructors() {
		DistanceMatrixDistance dm = new DistanceMatrixDistance();
		dm = new DistanceMatrixDistance("text", 1);
		assertNotNull(dm);
	}
	
	@Test
	public void getAndSetMethods() {
		DistanceMatrixDistance dm = new DistanceMatrixDistance();
		dm.setText("text");
		dm.setValue(1);
		assertTrue(dm.getText().equals("text"));
		assertTrue(dm.getValue().equals(1));
	}
}
