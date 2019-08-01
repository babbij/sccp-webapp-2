package com.colabriq.webapp.error;

import com.google.gson.annotations.Expose;

public class BadRequestException extends Exception {
	private static final long serialVersionUID = 1L;
	
	@Expose
	private final String message;
	
	public BadRequestException(String message) {
		super(message);
		this.message = message;
	}
	
	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}
}
