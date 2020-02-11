package com.revature.beans.googlemaps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceDetailsResponse {
  @JsonProperty("result")
  private PlaceDetails result;
 
  public PlaceDetails getResult() {
    return result;
  }
 
  public void setResult(PlaceDetails result) {
    this.result = result;
  }
}