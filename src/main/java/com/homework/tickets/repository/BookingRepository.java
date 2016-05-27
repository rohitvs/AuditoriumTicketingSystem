package com.homework.tickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homework.tickets.model.Booking;
import com.homework.tickets.model.SeatHold;

public interface BookingRepository extends JpaRepository<Booking, Integer>{

	@Query("select count(b) from Booking b inner join b.bookingSeatMap bsm "
			+ " inner join bsm.seats s where s.levelId=?1"
			)
	public Integer findCountOfBookingsInLevel(int levelId);
	
	@Query("select count(b) from Booking b inner join b.bookingSeatMap bsm "
			+ " inner join bsm.seats s "
			)
	public Integer findCountOfBookingsInAuditorium();
}
