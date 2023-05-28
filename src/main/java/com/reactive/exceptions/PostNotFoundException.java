package com.reactive.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PostNotFoundException extends ResponseStatusException {

	private static final long serialVersionUID = 1L;

	public PostNotFoundException(HttpStatus status, String message, Throwable e) {
		super(status, message, e);
	}

}