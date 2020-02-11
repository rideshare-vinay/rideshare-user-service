package com.revature.beans.googlemaps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceGeometry {
  @JsonProperty("location")
  private PlaceLocation location;
 
  public PlaceLocation getLocation() {
    return location;
  }
 
  public void setLocation(PlaceLocation location) {
    this.location = location;
  }
}
