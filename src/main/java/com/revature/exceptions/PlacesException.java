package com.revature.exceptions;

public class PlacesException extends RuntimeException {
	private static final long serialVersionUID = -8503826726317173608L;

	public PlacesException() {
		super();
	}

	public PlacesException(final String message) {
		super(message);
	}
}
