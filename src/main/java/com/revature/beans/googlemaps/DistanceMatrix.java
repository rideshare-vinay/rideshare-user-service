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
	
	@JsonProperty("destination_addresses")
	private List<String> destination_addresses = Collections.emptyList();
	
	@JsonProperty("origin_addresses")
	private List<String> origin_addresses = Collections.emptyList();
	
	@JsonProperty("status")
	private String status;
	
	public DistanceMatrix(){
		
	}
	
	public DistanceMatrix(List<DistanceMatrixRows> rows, List<String> destination_addresses,
			List<String> origin_addresses, String status) {
		super();
		this.rows = rows;
		this.destination_addresses = destination_addresses;
		this.origin_addresses = origin_addresses;
		this.status = status;
	}

	public List<DistanceMatrixRows> getRows() {
		return rows;
	}

	public void setRows(List<DistanceMatrixRows> rows) {
		this.rows = rows;
	}

	public List<String> getDestination_addresses() {
		return destination_addresses;
	}

	public void setDestination_addresses(List<String> destination_addresses) {
		this.destination_addresses = destination_addresses;
	}

	public List<String> getOrigin_addresses() {
		return origin_addresses;
	}

	public void setOrigin_addresses(List<String> origin_addresses) {
		this.origin_addresses = origin_addresses;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
