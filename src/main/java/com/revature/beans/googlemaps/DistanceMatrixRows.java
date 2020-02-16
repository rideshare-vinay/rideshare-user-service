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
public class DistanceMatrixRows {
	@JsonProperty("elements")
	private List<DistanceMatrixElements> elements = Collections.emptyList();

	public DistanceMatrixRows() {
		
	}
	
	public DistanceMatrixRows(List<DistanceMatrixElements> elements) {
		super();
		this.elements = elements;
	}

	public List<DistanceMatrixElements> getElements() {
		return elements;
	}

	public void setElements(List<DistanceMatrixElements> elements) {
		this.elements = elements;
	}
	
	
}
