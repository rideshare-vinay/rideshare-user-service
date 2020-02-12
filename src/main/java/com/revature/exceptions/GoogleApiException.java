package com.revature.exceptions;


/***********************************
 * 
 * This is an exception whenever the program makes google maps api calls to indicate some failure during the call.
 *
 ***********************************/
public class GoogleApiException extends RuntimeException{
	
	/**
	 * Auto generated key
	 */
	private static final long serialVersionUID = -8051230547691995173L;

	public GoogleApiException() {
		super();
		
	}

	public GoogleApiException(final String message) {
		super(message);
	}
}
