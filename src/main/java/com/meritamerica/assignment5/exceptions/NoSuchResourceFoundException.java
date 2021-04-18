package com.meritamerica.assignment5.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchResourceFoundException extends Exception {
	
	public NoSuchResourceFoundException(String msg) {
		super(msg);
	}
	

}
