package com.meritamerica.assignment5.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( HttpStatus.BAD_REQUEST )
public class InvalidRequestException extends Exception
{
	private static final long serialVersionUID = 1L;

	public InvalidRequestException( String msg )
	{
		super( msg );
	}
}
