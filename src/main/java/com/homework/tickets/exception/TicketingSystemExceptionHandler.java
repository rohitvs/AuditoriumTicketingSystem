/*package com.homework.tickets.exception;

import org.codehaus.groovy.control.messages.ExceptionMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@EnableWebMvc
public class TicketingSystemExceptionHandler extends ResponseEntityExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ IllegalArgumentException.class })
	@ResponseBody
	public ResponseEntity<String> handleBadRequestException(final Exception exception) {
		return errorResponse(exception, HttpStatus.BAD_REQUEST);
	}

	protected ResponseEntity<String> errorResponse(Throwable throwable, HttpStatus status) {
		if (null != throwable) {
			return response(throwable.getMessage(), status);
		} else {
			return response(null, status);
		}
	}

	protected <T> ResponseEntity<T> response(T body, HttpStatus status) {
		return new ResponseEntity<>(body, new HttpHeaders(), status);
	}
}
*/