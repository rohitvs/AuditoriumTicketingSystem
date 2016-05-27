package com.homework.tickets.dao;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.homework.tickets.core.SeatAssigner;
import com.homework.tickets.exception.NotAuthorizedException;
import com.homework.tickets.exception.SeatsNotAvailableException;
import com.homework.tickets.exception.SeatsNotAvailableExceptionTryAgainException;
import com.homework.tickets.exception.TicketHoldExpiredException;
import com.homework.tickets.exception.TicketHoldNotFoundException;
import com.homework.tickets.exception.TicketingServiceApplicationException;
import com.homework.tickets.model.AuditoriumMetaData;
import com.homework.tickets.model.Booking;
import com.homework.tickets.model.BookingSeatMap;
import com.homework.tickets.model.HoldingSeatMap;
import com.homework.tickets.model.SeatHold;
import com.homework.tickets.model.Seats;
import com.homework.tickets.repository.AuditoriumMetaDataRepository;
import com.homework.tickets.repository.BookingRepository;
import com.homework.tickets.repository.PropertiesRepository;
import com.homework.tickets.repository.SeatHoldRepository;
import com.homework.tickets.repository.SeatsRepository;

@Component
public class TicketingServiceDAOImpl implements TicketingServiceDAO {
	@Autowired
	public SeatsRepository seatsRepo;
	@Autowired
	public SeatHoldRepository seatHoldRepo;
	@Autowired
	public BookingRepository bookingRepo;
	@Autowired
	public PropertiesRepository propRepo;
	@Autowired
	public SeatAssigner<List<Seats>, Seats> seatAssigner;

	@Autowired
	private AuditoriumMetaDataRepository metaDataRepo;

	@Override
	@Transactional
	/**
	 * Gets total seats in a particular level
	 */
	public Integer getTotalSeatsInLevel(int levelId) throws TicketingServiceApplicationException {
		Integer totalSeats = null;
		try {
			totalSeats = seatsRepo.findSeatCountByLevelId(levelId);
		} catch (Exception e) {
			throw new TicketingServiceApplicationException("Error in getTotalSeatsInLevel " + e.getMessage());
		}
		return totalSeats;
	}

	@Override
	@Transactional
	/**
	 * Gets total seats in auditorium
	 */
	public Integer getTotalSeatsInAuditorium() throws TicketingServiceApplicationException {
		Integer totalSeats = null;
		try {
			totalSeats = seatsRepo.findTotalSeatCount();

		} catch (Exception e) {
			throw new TicketingServiceApplicationException("Error in getTotalSeatsInAuditorium " + e.getMessage());
		}
		return totalSeats;
	}

	/**
	 * Get Available seats in a particular level
	 */
	@Override
	@Transactional
	public Integer getAvailableSeatsInLevel(int levelId) throws TicketingServiceApplicationException {
		int totalAvailSeatsInLevel = 0;
		try {
			int totalSeatsInLevel = getTotalSeatsInLevel(levelId);
			List<SeatHold> seatHoldList = seatHoldRepo.findSeatHoldsInLevel(levelId);
			filterExpiredFromSeatHoldList(seatHoldList);
			int countBookings = bookingRepo.findCountOfBookingsInLevel(levelId);
			totalAvailSeatsInLevel = totalSeatsInLevel - seatHoldList.size() - countBookings;
		} catch (Exception e) {
			throw new TicketingServiceApplicationException("Error in getAvailableSeatsInLevel " + e.getMessage());
		}
		return totalAvailSeatsInLevel;
	}

	@Override
	@Transactional
	/**
	 * Get available seats in the Auditorium.
	 * 
	 */
	public Integer getAvailableSeatsInAuditorium() throws TicketingServiceApplicationException {
		int totalSeatsInAud = 0;
		try {
			totalSeatsInAud = getTotalSeatsInAuditorium();
			List<SeatHold> seatHoldList = seatHoldRepo.findSeatHoldsInAuditorium();
			filterExpiredFromSeatHoldList(seatHoldList);
			int countBookings = bookingRepo.findCountOfBookingsInAuditorium();
			totalSeatsInAud = totalSeatsInAud - seatHoldList.size() - countBookings;
		} catch (Exception e) {
			throw new TicketingServiceApplicationException("Error in getAvailableSeatsInLevel " + e.getMessage());
		}
		return totalSeatsInAud;
	}

	/**
	 * This method is to save the booking. If 2 save threads come through for the same SeatHoldId, only one will be saved.
	 * The first thread that acquires lock on the SeatHold Object will successfully save and the other thread will get TicketHoldExpiredException.
	 *  It does the following 
	 * 1. if the hold id is not found it throws TicketHoldNotFoundException 2. If the hold Id
	 * is expired it throws TicketHoldExpiredException 3. If 1 and 2 are false
	 * it constructs the booking object and saves it. 4. Once the booking is
	 * saved it updates the hold date to 1901/01/01 so that it will be deemed
	 * expired. 5. All of the above steps are in one single transaction. 6. if this holdId doesn't belong to the user, it throws NotAuthorizedException
	 */
	@Override
	@Transactional
	public String saveBooking(Integer holdId, String email)
			throws TicketingServiceApplicationException, TicketHoldExpiredException, TicketHoldNotFoundException {
		SeatHold seatHold = null;
		Booking booking;
		try {
			seatHold = seatHoldRepo.getOne(holdId);
			if(email!=null && !email.equalsIgnoreCase(seatHold.getEmail())){
				throw new NotAuthorizedException("You are unauthorized to save this booking!");
			}
			/**
			 * If two requests come together for the same seatHold object only one should get executed at one time
			 */
			synchronized (seatHold) {
				if (seatHold.isHoldExpired(Integer.parseInt(propRepo.getOne("wait_time").getValue()))) {
					throw new TicketHoldExpiredException("Hold Id: " + holdId + " Expired");
				} else {
					booking = constructBookingToSave(seatHold);
					if (booking != null) {
						booking = bookingRepo.saveAndFlush(booking);
						try {
							seatHold.setHoldDate(new Timestamp(getDefaultHoldDate()));
						} catch (ParseException e) {
							throw new TicketingServiceApplicationException(
									"Error in saveBooking while parsing the defalut expiry date" + e.getMessage());
						}
						seatHoldRepo.save(seatHold);
						seatHoldRepo.flush();
					}
				}
			}
		} catch (EntityNotFoundException e1) {
			throw new TicketHoldNotFoundException("Hold Id: " + holdId + " Not found");
		}
		return new StringBuffer().append(booking.getBookingId()).toString();
	}

	/**
	 * This method searches for best available seats and holds it for the
	 * customer. This method has been synchronized to make sure no 2 customers
	 * acquire hold to the same seat
	 */
	@Override
	@Transactional
	public synchronized SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) {
		int minLevelInt = 0;
		int maxLevelInt = 0;
		if (minLevel.isPresent()) {
			minLevelInt = minLevel.get();
		}
		if (maxLevel.isPresent()) {
			maxLevelInt = maxLevel.get();
		}
		// assign min level as 1 and maxLevel as total levels available. or make
		// them same if either one is 0
		if (minLevelInt == 0 && maxLevelInt == 0) {
			maxLevelInt = (int) metaDataRepo.count();
			minLevelInt = 1;
		} else if (minLevelInt == 0) {
			minLevelInt = maxLevelInt;
		} else if (maxLevelInt == 0) {
			maxLevelInt = minLevelInt;
		}
		Integer countBookings = bookingRepo.findCountOfBookingsInAuditorium();
		Integer totalSeats = seatsRepo.findTotalSeatCount();
		if (totalSeats == countBookings) {
			throw new SeatsNotAvailableException("No More seats Available!!");
		}

		List<Seats> assignedSeats = new ArrayList<Seats>();
		for (int i = minLevelInt; i <= maxLevelInt; i++) {
			// This list will not have any booked seats. They have been filtered
			// in the query
			List<Seats> seats = seatsRepo.findAllNonBookedSeatsInLevel(i);
			sortSeats(seats);
			// filter out holds that are still active
			filterActiveHoldsFromSeatsList(seats);
			assignedSeats.addAll(seatAssigner.assignSeatsInSameLevel(seats, numSeats));
			if (assignedSeats != null && assignedSeats.size() == numSeats) {
				break;
			}

		}
		if (assignedSeats.isEmpty() && minLevelInt != maxLevelInt) {
			// logic to assign first available seats from min and max levels
			List<Seats> seats = seatsRepo.findAllNonBookedSeatsInAuditorium();
			if (seats.size() < numSeats) {
				throw new SeatsNotAvailableException("No more Seats Available in the levels you requested.");
			}
			sortSeats(seats);
			// filter out holds that are still active
			filterActiveHoldsFromSeatsList(seats, minLevelInt, maxLevelInt);
			if (seats.size() < numSeats) {
				throw new SeatsNotAvailableExceptionTryAgainException(
						"There are no more seats available at this moment! Please try again after some time.");
			} else {
				for (int i = 0; i < numSeats; i++) {
					assignedSeats.add(seats.get(i));
				}
			}
		}
		if (assignedSeats.isEmpty()) {
			throw new SeatsNotAvailableException("No more Seats Available in the levels you requested.");
		}
		SeatHold seatHold = constructHoldToSave(customerEmail, assignedSeats);
		seatHold = seatHoldRepo.saveAndFlush(seatHold);
		return seatHold;
	}

	private static void sortSeats(List<Seats> seats) {

		Collections.sort(seats, new Comparator<Seats>() {

			public int compare(Seats o1, Seats o2) {
				Integer x1 = ((Seats) o1).getLevelId();
				Integer x2 = ((Seats) o2).getLevelId();
				int sComp1 = x1.compareTo(x2);

				if (sComp1 != 0) {
					return sComp1;
				} else {
					Integer x3 = ((Seats) o1).getRowId();
					Integer x4 = ((Seats) o2).getRowId();
					int sComp2 = x3.compareTo(x4);

					if (sComp2 != 0) {
						return sComp2;
					} else {
						Integer x5 = ((Seats) o1).getSeatId();
						Integer x6 = ((Seats) o2).getSeatId();
						return x5.compareTo(x6);
					}
				}
			}
		});
	}

	private long getDefaultHoldDate() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dateFormat.parse("01/01/1901");
		long time = date.getTime();
		return time;
	}

	private void filterExpiredFromSeatHoldList(List<SeatHold> seatHoldList) {
		Iterator<SeatHold> it = seatHoldList.iterator();
		while (it.hasNext()) {
			SeatHold sh = (SeatHold) it.next();
			if (sh.isHoldExpired(Integer.parseInt(propRepo.getOne("wait_time").getValue()))) {
				it.remove();
			}
		}
	}

	/**
	 * removes seats holds that are still active
	 * 
	 * @param seatsList
	 */
	private void filterActiveHoldsFromSeatsList(List<Seats> seatsList) {
		Iterator<Seats> it = seatsList.iterator();
		List<Integer> activeHoldSeatIds = new ArrayList<Integer>();
		while (it.hasNext()) {
			Seats s = (Seats) it.next();
			if (s.getHoldingSeatMap() != null) {
				for (HoldingSeatMap hsm : s.getHoldingSeatMap()) {
					SeatHold sh = hsm.getSeatHold();
					if (sh != null && !sh.isHoldExpired(Integer.parseInt(propRepo.getOne("wait_time").getValue()))) {
						activeHoldSeatIds.add(s.getSeatId());
						it.remove();
					}
				}
			}
		}
		/*
		 * At this point the filtered seat list may still have a seat that has
		 * an active hold. This may happen when there are multiple holds(one
		 * expired and one active) on the same seat.
		 * 
		 */
		Iterator<Seats> it2 = seatsList.iterator();
		while (it2.hasNext()) {
			if (activeHoldSeatIds.contains(it2.next().getSeatId())) {
				it2.remove();
			}
		}
	}

	/**
	 * Removes seats outside levels and also removes seats holds that are still
	 * active
	 * 
	 * @param seatsList
	 * @param minLevel
	 * @param maxLevel
	 */
	private void filterActiveHoldsFromSeatsList(List<Seats> seatsList, int minLevel, int maxLevel) {
		Iterator<Seats> it = seatsList.iterator();
		List<Integer> activeHoldSeatIds = new ArrayList<Integer>();
		while (it.hasNext()) {
			Seats s = (Seats) it.next();
			if (s.getHoldingSeatMap() != null) {
				/*
				 * First remove if the seat is out side of the levels requested
				 */
				if (s.getLevelId() >= maxLevel && s.getLevelId() <= minLevel) {
					it.remove();
				} else {
					for (HoldingSeatMap hsm : s.getHoldingSeatMap()) {
						SeatHold sh = hsm.getSeatHold();
						if (sh != null
								&& !sh.isHoldExpired(Integer.parseInt(propRepo.getOne("wait_time").getValue()))) {
							System.out.println("Removing seatid:" + s.getSeatId());
							activeHoldSeatIds.add(s.getSeatId());
							it.remove();
						}
					}
				}
			}
		}

		/*
		 * At this point the filtered seat list may still have a seat that has
		 * an active hold. This may happen when there are multiple holds(one
		 * expired and one active) on the same seat.
		 * 
		 */
		Iterator<Seats> it2 = seatsList.iterator();
		while (it2.hasNext()) {
			if (activeHoldSeatIds.contains(it2.next().getSeatId())) {
				it2.remove();
			}
		}
	}

	private Booking constructBookingToSave(SeatHold seatHold) {
		Booking booking = new Booking();
		booking.setBookingDate(new Timestamp(System.currentTimeMillis()));
		booking.setEmail(seatHold.getEmail());
		booking.setBookingSeatMap(constructBookingSeatMapToSave(seatHold.getHoldingSeatMap()));
		for (BookingSeatMap bookMap : booking.getBookingSeatMap()) {
			bookMap.setBooking(booking);
		}
		return booking;
	}

	private SeatHold constructHoldToSave(String email, List<Seats> assignedSeats) {
		SeatHold seatHold = new SeatHold();
		seatHold.setHoldDate(new Timestamp(System.currentTimeMillis()));
		seatHold.setEmail(email);
		seatHold.setHoldingSeatMap(constructHoldingSeatMapToSave(assignedSeats));
		for (HoldingSeatMap holdMap : seatHold.getHoldingSeatMap()) {
			holdMap.setSeatHold(seatHold);
		}
		return seatHold;
	}

	private List<BookingSeatMap> constructBookingSeatMapToSave(List<HoldingSeatMap> holdingSeatMap) {
		List<BookingSeatMap> bookingSeatMap = new ArrayList<BookingSeatMap>();
		for (HoldingSeatMap holdmap : holdingSeatMap) {
			BookingSeatMap bookingMap = new BookingSeatMap();
			bookingMap.setSeats(holdmap.getSeats());
			bookingSeatMap.add(bookingMap);
		}
		return bookingSeatMap;
	}

	private List<HoldingSeatMap> constructHoldingSeatMapToSave(List<Seats> assignedSeats) {
		List<HoldingSeatMap> holdingSeatMap = new ArrayList<HoldingSeatMap>();
		for (int i = 0; i < assignedSeats.size(); i++) {
			HoldingSeatMap holdingMap = new HoldingSeatMap();
			holdingMap.setSeats(assignedSeats.get(i));
			holdingSeatMap.add(holdingMap);
		}

		return holdingSeatMap;
	}

}
