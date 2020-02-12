package com.revature.beans.googlemaps;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DistanceMatrix {
	@JsonProperty("rows")
	private List<DistanceMatrixRows> rows = Collections.emptyList();

	public DistanceMatrix(){
		
	}
	
	public DistanceMatrix(List<DistanceMatrixRows> rows) {
		super();
		this.rows = rows;
	}

	public List<DistanceMatrixRows> getRows() {
		return rows;
	}

	public void setRows(List<DistanceMatrixRows> rows) {
		this.rows = rows;
	}
	
	
}
