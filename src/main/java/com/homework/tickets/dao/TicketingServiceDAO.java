package com.homework.tickets.dao;

import java.util.Optional;

import com.homework.tickets.exception.TicketHoldExpiredException;
import com.homework.tickets.exception.TicketHoldNotFoundException;
import com.homework.tickets.exception.TicketingServiceApplicationException;
import com.homework.tickets.model.SeatHold;

public interface TicketingServiceDAO {

	public Integer getTotalSeatsInLevel(int levelId) throws TicketingServiceApplicationException;
	
	public Integer getTotalSeatsInAuditorium() throws TicketingServiceApplicationException;
	
	public Integer getAvailableSeatsInLevel(int levelId) throws TicketingServiceApplicationException;
	
	public Integer getAvailableSeatsInAuditorium() throws TicketingServiceApplicationException;
	
	public String saveBooking(Integer holdId, String email) throws TicketingServiceApplicationException, TicketHoldExpiredException, TicketHoldNotFoundException;
	
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) throws TicketingServiceApplicationException;
}
