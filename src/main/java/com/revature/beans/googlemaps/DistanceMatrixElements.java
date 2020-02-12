package com.revature.beans.googlemaps;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
