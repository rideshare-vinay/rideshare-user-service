package com.revature.beans.googlemaps;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;



public class DistanceMatrixTest {

	@Test
	public void testConstructors() {
		DistanceMatrix dm = new DistanceMatrix();
		dm = new DistanceMatrix(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), "status");
		assertNotNull(dm);
	}
	
	@Test
	public void getAndSetMethods() {
		DistanceMatrix dm = new DistanceMatrix();
		dm.setdestinationAddresses(Collections.emptyList());
		dm.setoriginAddresses(Collections.emptyList());
		dm.setRows(Collections.emptyList());
		dm.setStatus("status");
		assertTrue(dm.getdestinationAddresses().equals(Collections.emptyList()));
		assertTrue(dm.getoriginAddresses().equals(Collections.emptyList()));
		assertTrue(dm.getRows().equals(Collections.emptyList()));
		assertTrue(dm.getStatus().equals("status"));

	}

}
