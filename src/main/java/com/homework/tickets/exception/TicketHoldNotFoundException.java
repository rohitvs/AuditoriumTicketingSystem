package com.homework.tickets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND,reason="This Ticket Hold Id coudnot be found")
public class TicketHoldNotFoundException extends TicketingServiceApplicationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3540775854885247914L;
	private String message = null;

	public TicketHoldNotFoundException() {
		super();
	}

	public TicketHoldNotFoundException(String message) {
		super(message);
		this.message = message;
	}

	public TicketHoldNotFoundException(Throwable cause) {
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
