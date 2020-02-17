package com.revature.beans.googlemaps;

import static org.junit.Assert.*;

import org.junit.Test;

public class DistanceMatrixRowsTest {

	@Test
	public void testConstructors() {
		DistanceMatrixRows dmr = new DistanceMatrixRows();
		dmr = new DistanceMatrixRows(null);
		assertNotNull(dmr);
	}
	
	@Test
	public void getAndSetMethods() {
		DistanceMatrixRows dmr = new DistanceMatrixRows();
		dmr.setElements(null);
		assertNull(dmr.getElements());
	}

}
