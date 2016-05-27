package com.homework.tickets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE,reason="This Ticket Hold Id has already expired")
public class TicketHoldExpiredException extends TicketingServiceApplicationException{

	private String message = null;

	public TicketHoldExpiredException() {
		super();
	}

	public TicketHoldExpiredException(String message) {
		super(message);
		this.message = message;
	}

	public TicketHoldExpiredException(Throwable cause) {
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
