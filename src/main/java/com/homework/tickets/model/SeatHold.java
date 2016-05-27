package com.homework.tickets.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "seat_hold")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class SeatHold {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hold_id")
	private Integer holdId;

	@Column(name = "hold_date")
	private Timestamp holdDate;

	@Column(name = "email")
	private String email;
	
	@OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL, mappedBy = "seatHold", targetEntity = HoldingSeatMap.class)
	private List<HoldingSeatMap> holdingSeatMap;

	public Integer getHoldId() {
		return holdId;
	}

	public void setHoldId(Integer holdId) {
		this.holdId = holdId;
	}

	public Timestamp getHoldDate() {
		return holdDate;
	}

	public void setHoldDate(Timestamp holdDate) {
		this.holdDate = holdDate;
	}

	public SeatHold() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((holdDate == null) ? 0 : holdDate.hashCode());
		result = prime * result + ((holdId == null) ? 0 : holdId.hashCode());
		result = prime * result + ((holdingSeatMap == null) ? 0 : holdingSeatMap.hashCode());
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
		SeatHold other = (SeatHold) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (holdDate == null) {
			if (other.holdDate != null)
				return false;
		} else if (!holdDate.equals(other.holdDate))
			return false;
		if (holdId == null) {
			if (other.holdId != null)
				return false;
		} else if (!holdId.equals(other.holdId))
			return false;
		if (holdingSeatMap == null) {
			if (other.holdingSeatMap != null)
				return false;
		} else if (!holdingSeatMap.equals(other.holdingSeatMap))
			return false;
		return true;
	}

	

	@Override
	public String toString() {
		return "SeatHold [holdId=" + holdId + ", holdDate=" + holdDate + ", email=" + email + ", holdingSeatMap="
				+ holdingSeatMap + "]";
	}

	public List<HoldingSeatMap> getHoldingSeatMap() {
		return holdingSeatMap;
	}

	public void setHoldingSeatMap(List<HoldingSeatMap> holdingSeatMap) {
		this.holdingSeatMap = holdingSeatMap;
	}

	public boolean isHoldExpired(int waitTime) {
		long currentDate = System.currentTimeMillis();
		Timestamp currentTime = new Timestamp(currentDate);
		Timestamp original = new Timestamp(this.holdDate.getTime());
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(original.getTime());
		cal.add(Calendar.SECOND, waitTime);
		Timestamp later = new Timestamp(cal.getTime().getTime());
		return currentTime.after(later);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
