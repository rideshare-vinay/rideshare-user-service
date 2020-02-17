package com.revature.beans.googlemaps;

import static org.junit.Assert.*;

import org.junit.Test;

public class DistanceMatrixElementsTest {

	@Test
	public void testConstructors() {
		DistanceMatrixElements dme = new DistanceMatrixElements();
		dme = new DistanceMatrixElements(null, null, "status");
		assertNotNull(dme);
	}
	
	@Test
	public void getAndSetMethods() {
		DistanceMatrixElements dme = new DistanceMatrixElements();
		dme.setDistance(null);
		dme.setDuration(null);
		dme.setStatus("status");
		assertNull(dme.getDistance());
		assertNull(dme.getDuration());
		assertTrue(dme.getStatus().equals("status"));
	}
}
