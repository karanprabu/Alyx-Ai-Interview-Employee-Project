package com.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidInputException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public InvalidInputException(String message) {
        super(message);
    }
}
