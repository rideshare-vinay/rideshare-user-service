package com.revature.beans.googlemaps;

import static org.junit.Assert.*;

import org.junit.Test;

public class DistanceMatrixDurationTest {

	@Test
	public void testConstructors() {
		DistanceMatrixDuration dm = new DistanceMatrixDuration();
		dm = new DistanceMatrixDuration("text", 1);
		assertNotNull(dm);
	}
	
	@Test
	public void getAndSetMethods() {
		DistanceMatrixDuration dm = new DistanceMatrixDuration();
		dm.setText("text");
		dm.setValue(1);
		assertTrue(dm.getText().equals("text"));
		assertTrue(dm.getValue().equals(1));
	}

}
