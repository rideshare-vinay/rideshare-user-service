package com.revature.exceptions;

public class PlacesException extends RuntimeException{
	
	public PlacesException() {
		super();
		
	}

	public PlacesException(final String message) {
		super(message);
	}
}
