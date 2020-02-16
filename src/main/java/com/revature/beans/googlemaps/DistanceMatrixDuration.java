package com.revature.beans.googlemaps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**********************************
 * 
 * All of the DistanceMatrix*** classes are used to deconstruct the json returned by the google maps api distance matrix call.
 * This particular class is not currently used (11Feb2020) for the ride recommendation algorithm.
 * However, it has been left in the code base to enable the algorithm to make recommendations based upon
 * drive time rather than drive distance if future developers choose to do so.
 *
 **********************************/
@JsonIgnoreProperties(ignoreUnknown = true)
public class DistanceMatrixDuration {
	@JsonProperty("text")
	private String text;
	
	@JsonProperty("value")
	private Integer value;
	
	public DistanceMatrixDuration() {
		
	}

	public DistanceMatrixDuration(String text, Integer value) {
		super();
		this.text = text;
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
}
