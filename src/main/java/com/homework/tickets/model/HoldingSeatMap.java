package com.homework.tickets.model;

import javax.persistence.CascadeType;
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
@Table(name="holding_seat_map")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class HoldingSeatMap {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="hold_seat_map_id")
	private Integer holdSeatMapId;
	
	@ManyToOne()
    @JoinColumn(name="hold_id")
	private SeatHold seatHold;

	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="seat_id")
	private Seats seats;

	public Integer getHoldSeatMapId() {
		return holdSeatMapId;
	}

	public void setHoldSeatMapId(Integer holdSeatMapId) {
		this.holdSeatMapId = holdSeatMapId;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((holdSeatMapId == null) ? 0 : holdSeatMapId.hashCode());
		result = prime * result + ((seatHold == null) ? 0 : seatHold.hashCode());
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
		HoldingSeatMap other = (HoldingSeatMap) obj;
		if (holdSeatMapId == null) {
			if (other.holdSeatMapId != null)
				return false;
		} else if (!holdSeatMapId.equals(other.holdSeatMapId))
			return false;
		if (seatHold == null) {
			if (other.seatHold != null)
				return false;
		} else if (!seatHold.equals(other.seatHold))
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
		return "HoldingSeatMap [holdSeatMapId=" + holdSeatMapId + ", seatHold=" + seatHold + ", seats=" + seats + "]";
	}

	

	public HoldingSeatMap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SeatHold getSeatHold() {
		return seatHold;
	}

	public void setSeatHold(SeatHold seatHold) {
		this.seatHold = seatHold;
	}

	public void setSeats(Seats seats) {
		this.seats = seats;
	}

	public Seats getSeats() {
		return seats;
	}

	
	
	
}
