package com.eidiko.employee.exception;

public class PasswordNotValidException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordNotValidException() {
		super();
	}

	public PasswordNotValidException(String message) {
		super(message);
	}

}
