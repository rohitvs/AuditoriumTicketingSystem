package com.homework.tickets.core;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.tickets.dao.TicketingServiceDAO;
import com.homework.tickets.model.SeatHold;

@Service
public class TicketingServiceImpl implements TicketingService {

	@Autowired
	private TicketingServiceDAO ticketingServiceDao;

	/**
	 * This service returns the total seats available in a level if the levelid is provided.
	 * Else it returns total number of seats in the auditorium
	 */
	@Override
	public int numSeatsAvailable(Optional<Integer> venueLevel) {
		int totalSeats = 0;
		if (venueLevel.isPresent()) {
			totalSeats = ticketingServiceDao.getAvailableSeatsInLevel(venueLevel.get());
		} else {
			totalSeats=ticketingServiceDao.getAvailableSeatsInAuditorium();
		}

		return totalSeats;
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		String confirmationNumber=ticketingServiceDao.saveBooking(seatHoldId, customerEmail);
		return confirmationNumber;
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) {
		SeatHold seatHold=ticketingServiceDao.findAndHoldSeats(numSeats, minLevel, maxLevel, customerEmail);
		return seatHold;
	}

}
