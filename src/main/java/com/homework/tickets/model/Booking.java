package com.homework.tickets.model;

import java.sql.Timestamp;
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
@Table(name="booking")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Booking {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="booking_id")
	private Integer bookingId;
	
	
	@Column(name="booking_date")
	private Timestamp bookingDate;
	
	@Column(name = "email")
	private String email;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL,mappedBy = "booking", targetEntity = BookingSeatMap.class)
	private List<BookingSeatMap> bookingSeatMap;
	

	public Integer getBookingId() {
		return bookingId;
	}
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}
	/*public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}*/
	public Timestamp getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Timestamp bookingDate) {
		this.bookingDate = bookingDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookingDate == null) ? 0 : bookingDate.hashCode());
		result = prime * result + ((bookingId == null) ? 0 : bookingId.hashCode());
		result = prime * result + ((bookingSeatMap == null) ? 0 : bookingSeatMap.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		Booking other = (Booking) obj;
		if (bookingDate == null) {
			if (other.bookingDate != null)
				return false;
		} else if (!bookingDate.equals(other.bookingDate))
			return false;
		if (bookingId == null) {
			if (other.bookingId != null)
				return false;
		} else if (!bookingId.equals(other.bookingId))
			return false;
		if (bookingSeatMap == null) {
			if (other.bookingSeatMap != null)
				return false;
		} else if (!bookingSeatMap.equals(other.bookingSeatMap))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	
	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", bookingDate=" + bookingDate + ", email=" + email
				+ ", bookingSeatMap=" + bookingSeatMap + "]";
	}
	
	
	
	public List<BookingSeatMap> getBookingSeatMap() {
		return bookingSeatMap;
	}
	public void setBookingSeatMap(List<BookingSeatMap> bookingSeatMap) {
		this.bookingSeatMap = bookingSeatMap;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
