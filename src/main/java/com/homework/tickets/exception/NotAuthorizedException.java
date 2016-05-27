package com.homework.tickets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED,reason="You are unauthorized to save this booking!")
public class NotAuthorizedException extends TicketingServiceApplicationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5578680467823517719L;

	/**
	 * 
	 */
	private String message = null;

	public NotAuthorizedException() {
		super();
	}

	public NotAuthorizedException(String message) {
		super(message);
		this.message = message;
	}

	public NotAuthorizedException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
