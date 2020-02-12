package com.revature.beans.googlemaps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
