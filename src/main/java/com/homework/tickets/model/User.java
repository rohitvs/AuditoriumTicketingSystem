package com.homework.tickets.model;
/*package com.homework.tickets.entity;

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

@Entity
@Table(name="users")
public class User {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="user_id")
	private Integer userId;
	private String email;
	@OneToMany(cascade=CascadeType.REMOVE, fetch=FetchType.LAZY,
		       mappedBy="users",targetEntity=SeatHold.class)
	private List<SeatHold> seatHold;
	
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.REMOVE, 
		       mappedBy="users",targetEntity=Booking.class)
    private List<Booking> booking;
	

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public User(Integer userId, String email) {
		super();
		this.userId = userId;
		this.email = email;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", seatHold=" + seatHold + ", booking=" + booking + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((booking == null) ? 0 : booking.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((seatHold == null) ? 0 : seatHold.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		User other = (User) obj;
		if (booking == null) {
			if (other.booking != null)
				return false;
		} else if (!booking.equals(other.booking))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (seatHold == null) {
			if (other.seatHold != null)
				return false;
		} else if (!seatHold.equals(other.seatHold))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	public List<SeatHold> getSeatHold() {
		return seatHold;
	}
	public void setSeatHold(List<SeatHold> seatHold) {
		this.seatHold = seatHold;
	}
	public List<Booking> getBooking() {
		return booking;
	}
	public void setBooking(List<Booking> booking) {
		this.booking = booking;
	}
	public User(Integer userId, String email, List<SeatHold> seatHold, List<Booking> booking) {
		super();
		this.userId = userId;
		this.email = email;
		this.seatHold = seatHold;
		this.booking = booking;
	}
	
	
	
	
	
	
	
}
*/