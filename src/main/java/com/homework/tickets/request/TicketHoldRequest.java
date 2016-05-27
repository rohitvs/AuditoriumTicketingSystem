package com.homework.tickets.request;

public class TicketHoldRequest {

	private String email;
	private Integer numSeats;
	private Integer minLevel;
	private Integer maxLevel;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getNumSeats() {
		return numSeats;
	}
	public void setNumSeats(Integer numSeats) {
		this.numSeats = numSeats;
	}
	public Integer getMinLevel() {
		return minLevel;
	}
	public void setMinLevel(Integer minLevel) {
		this.minLevel = minLevel;
	}
	public Integer getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(Integer maxLevel) {
		this.maxLevel = maxLevel;
	}
	public TicketHoldRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "TicketHoldRequest [email=" + email + ", numSeats=" + numSeats + ", minLevel=" + minLevel + ", maxLevel="
				+ maxLevel + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((maxLevel == null) ? 0 : maxLevel.hashCode());
		result = prime * result + ((minLevel == null) ? 0 : minLevel.hashCode());
		result = prime * result + ((numSeats == null) ? 0 : numSeats.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TicketHoldRequest other = (TicketHoldRequest) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (maxLevel == null) {
			if (other.maxLevel != null)
				return false;
		} else if (!maxLevel.equals(other.maxLevel))
			return false;
		if (minLevel == null) {
			if (other.minLevel != null)
				return false;
		} else if (!minLevel.equals(other.minLevel))
			return false;
		if (numSeats == null) {
			if (other.numSeats != null)
				return false;
		} else if (!numSeats.equals(other.numSeats))
			return false;
		return true;
	}
	
	
	
}
