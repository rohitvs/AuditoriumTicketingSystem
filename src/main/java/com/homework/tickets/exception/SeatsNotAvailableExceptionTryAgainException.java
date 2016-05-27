package com.homework.tickets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND,reason="There are no more seats available at this moment! Please try again after some time.")
public class SeatsNotAvailableExceptionTryAgainException extends TicketingServiceApplicationException{


	/**
	 * 
	 */
	private String message = null;

	public SeatsNotAvailableExceptionTryAgainException() {
		super();
	}

	public SeatsNotAvailableExceptionTryAgainException(String message) {
		super(message);
		this.message = message;
	}

	public SeatsNotAvailableExceptionTryAgainException(Throwable cause) {
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
