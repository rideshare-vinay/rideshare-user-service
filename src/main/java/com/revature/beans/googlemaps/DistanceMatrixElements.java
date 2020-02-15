package com.revature.beans.googlemaps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**********************************
 * 
 * All of the DistanceMatrix*** classes are used to deconstruct the json returned by the google maps api distance matrix call.
 *
 **********************************/
@JsonIgnoreProperties(ignoreUnknown = true)
public class DistanceMatrixElements {
	@JsonProperty("distance")
	private DistanceMatrixDistance distance;
	
	@JsonProperty("duration")
	private DistanceMatrixDuration duration;
	
	@JsonProperty("status")
	private String status;

	public DistanceMatrixElements() {
		
	}
	
	public DistanceMatrixElements(DistanceMatrixDistance distance, DistanceMatrixDuration duration, String status) {
		super();
		this.distance = distance;
		this.duration = duration;
		this.status = status;
	}

	public DistanceMatrixDistance getDistance() {
		return distance;
	}

	public void setDistance(DistanceMatrixDistance distance) {
		this.distance = distance;
	}

	public DistanceMatrixDuration getDuration() {
		return duration;
	}

	public void setDuration(DistanceMatrixDuration duration) {
		this.duration = duration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
