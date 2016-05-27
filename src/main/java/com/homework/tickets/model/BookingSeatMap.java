package com.homework.tickets.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "booking_seat_map")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class BookingSeatMap {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_seat_map_id")
	private Integer holdSeatMapId;

	@ManyToOne()
	@JoinColumn(name = "booking_id")
	private Booking booking;

	@OneToOne()
	@JoinColumn(name = "seat_id")
	private Seats seats;

	public Integer getHoldSeatMapId() {
		return holdSeatMapId;
	}

	public void setHoldSeatMapId(Integer holdSeatMapId) {
		this.holdSeatMapId = holdSeatMapId;
	}

	public BookingSeatMap() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((booking == null) ? 0 : booking.hashCode());
		result = prime * result + ((holdSeatMapId == null) ? 0 : holdSeatMapId.hashCode());
		result = prime * result + ((seats == null) ? 0 : seats.hashCode());
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
		BookingSeatMap other = (BookingSeatMap) obj;
		if (booking == null) {
			if (other.booking != null)
				return false;
		} else if (!booking.equals(other.booking))
			return false;
		if (holdSeatMapId == null) {
			if (other.holdSeatMapId != null)
				return false;
		} else if (!holdSeatMapId.equals(other.holdSeatMapId))
			return false;
		if (seats == null) {
			if (other.seats != null)
				return false;
		} else if (!seats.equals(other.seats))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BookingSeatMap [holdSeatMapId=" + holdSeatMapId + ", booking=" + booking + ", seats=" + seats + "]";
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Seats getSeats() {
		return seats;
	}

	public void setSeats(Seats seats) {
		this.seats = seats;
	}

}
