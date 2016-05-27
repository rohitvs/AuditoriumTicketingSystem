package com.homework.tickets.response;

import java.io.Serializable;

public class NumSeatsResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7865826825504442228L;
	Integer numSeats;

	public Integer getNumSeats() {
		return numSeats;
	}

	public void setNumSeats(Integer numSeats) {
		this.numSeats = numSeats;
	}

	@Override
	public String toString() {
		return "NumSeatsResponse [numSeats=" + numSeats + "]";
	}
	
	
}
