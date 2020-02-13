package com.revature.beans.googlemaps;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**********************************
 * 
 * All of the DistanceMatrix*** classes are used to deconstruct the json returned by the google maps api distance matrix call.
 *
 **********************************/

@JsonIgnoreProperties(ignoreUnknown = true)
public class DistanceMatrix {
	@JsonProperty("rows")
	private List<DistanceMatrixRows> rows = Collections.emptyList();
	
	@JsonProperty("destinationAddresses")
	private List<String> destinationAddresses = Collections.emptyList();
	
	@JsonProperty("originAddresses")
	private List<String> originAddresses = Collections.emptyList();
	
	@JsonProperty("status")
	private String status;
	
	public DistanceMatrix(){
		
	}
	
	public DistanceMatrix(List<DistanceMatrixRows> rows, List<String> destinationAddresses,
			List<String> originAddresses, String status) {
		super();
		this.rows = rows;
		this.destinationAddresses = destinationAddresses;
		this.originAddresses = originAddresses;
		this.status = status;
	}

	public List<DistanceMatrixRows> getRows() {
		return rows;
	}

	public void setRows(List<DistanceMatrixRows> rows) {
		this.rows = rows;
	}

	public List<String> getdestinationAddresses() {
		return destinationAddresses;
	}

	public void setdestinationAddresses(List<String> destinationAddresses) {
		this.destinationAddresses = destinationAddresses;
	}

	public List<String> getoriginAddresses() {
		return originAddresses;
	}

	public void setoriginAddresses(List<String> originAddresses) {
		this.originAddresses = originAddresses;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
