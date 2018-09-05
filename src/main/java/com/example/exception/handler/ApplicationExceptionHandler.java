package com.example.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.exception.InvalidDataException;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<String> handleOtherExceptions(Exception exception) {
		//send alert mail to dev
		exception.printStackTrace();
		return new ResponseEntity<>("Oops Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(InvalidDataException.class)
	protected ResponseEntity<String> handleApplicationException(InvalidDataException exception) {
		return new ResponseEntity<>(exception.getErrorMessage(), HttpStatus.BAD_REQUEST);
	}
}
