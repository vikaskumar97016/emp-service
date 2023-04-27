package com.eidiko.employee.exception;

import java.io.IOException;

public class TokenNotValidException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenNotValidException() {
		super();
		
	}

	public TokenNotValidException(String message) {
		super(message);
		
	}

}
