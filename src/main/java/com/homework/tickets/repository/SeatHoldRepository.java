package com.homework.tickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homework.tickets.model.SeatHold;

public interface SeatHoldRepository extends JpaRepository<SeatHold, Integer>{

	@Query("select sh from SeatHold sh inner join sh.holdingSeatMap hsm "
			+ " inner join hsm.seats s where s.levelId=?1"
			)
	public List<SeatHold> findSeatHoldsInLevel(int levelId);
	
	@Query("select sh from SeatHold sh inner join sh.holdingSeatMap hsm "
			+ " inner join hsm.seats s "
			)
	public List<SeatHold> findSeatHoldsInAuditorium();
	
	@Query("select sh from SeatHold sh where sh.email=?1")
	public List<SeatHold> fildSeatHoldsForUser(String email);
}
