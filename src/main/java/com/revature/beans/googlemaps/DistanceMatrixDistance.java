package com.revature.beans.googlemaps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**********************************
 * 
 * All of the DistanceMatrix*** classes are used to deconstruct the json returned by the google maps api distance matrix call.
 *
 **********************************/
@JsonIgnoreProperties(ignoreUnknown = true)
public class DistanceMatrixDistance {
	@JsonProperty("text")
	private String text;
	
	@JsonProperty("value")
	private Integer value;
	
	public DistanceMatrixDistance() {
		
	}
	
	public DistanceMatrixDistance(String text, Integer value) {
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
