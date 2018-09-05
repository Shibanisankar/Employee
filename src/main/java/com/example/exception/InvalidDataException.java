package com.example.exception;

public class InvalidDataException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String errorMessage;
	
	public InvalidDataException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
