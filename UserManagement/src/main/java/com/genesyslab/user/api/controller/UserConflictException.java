package com.genesyslab.user.api.controller;

public class UserConflictException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public UserConflictException(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}
	
}
