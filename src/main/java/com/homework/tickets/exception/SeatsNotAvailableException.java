package com.homework.tickets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND,reason="There are no more seats available at this moment in the levels you requested.")
public class SeatsNotAvailableException extends TicketingServiceApplicationException{


	/**
	 * 
	 */
	private String message = null;

	public SeatsNotAvailableException() {
		super();
	}

	public SeatsNotAvailableException(String message) {
		super(message);
		this.message = message;
	}

	public SeatsNotAvailableException(Throwable cause) {
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
