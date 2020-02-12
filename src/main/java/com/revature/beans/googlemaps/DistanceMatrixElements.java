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

	public DistanceMatrixElements() {
		
	}
	
	public DistanceMatrixElements(DistanceMatrixDistance distance, DistanceMatrixDuration duration) {
		super();
		this.distance = distance;
		this.duration = duration;
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
	
	
}
