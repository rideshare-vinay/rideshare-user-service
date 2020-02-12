package com.revature.exceptions;

public class GoogleApiException extends RuntimeException{
	
	public GoogleApiException() {
		super();
		
	}

	public GoogleApiException(final String message) {
		super(message);
	}
}
