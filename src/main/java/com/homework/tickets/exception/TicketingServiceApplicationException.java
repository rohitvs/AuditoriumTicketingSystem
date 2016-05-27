package com.homework.tickets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR,reason="There was a problem processing this request")
public class TicketingServiceApplicationException extends RuntimeException {

	private String message = null;

	public TicketingServiceApplicationException() {
		super();
	}

	public TicketingServiceApplicationException(String message) {
		super(message);
		this.message = message;
	}

	public TicketingServiceApplicationException(Throwable cause) {
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
