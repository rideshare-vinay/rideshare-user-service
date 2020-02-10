package com.revature.beans.googlemaps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceDetails {
	@JsonProperty("name")
	private String name;
	 
	@JsonProperty("icon")
	private String icon;
	 
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("formatted_address")
	private String address;
	 
	@JsonProperty("geometry")
	private PlaceGeometry geometry;
	 
	public PlaceDetails() {
		
	}

	public PlaceDetails(String name, String icon, String url, String address, PlaceGeometry geometry) {
		super();
		this.name = name;
		this.icon = icon;
		this.url = url;
		this.address = address;
		this.geometry = geometry;
		//this.photos = photos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public PlaceGeometry getGeometry() {
		return geometry;
	}

	public void setGeometry(PlaceGeometry geometry) {
		this.geometry = geometry;
	}
  
}