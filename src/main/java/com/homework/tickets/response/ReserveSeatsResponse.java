package com.homework.tickets.response;

import java.io.Serializable;

public class ReserveSeatsResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8789196997415874172L;
	String confirmationNumber;
	public String getConfirmationNumber() {
		return confirmationNumber;
	}
	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
