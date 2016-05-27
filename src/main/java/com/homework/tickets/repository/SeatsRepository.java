package com.homework.tickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homework.tickets.model.Seats;

public interface SeatsRepository extends JpaRepository<Seats, Integer>{

	@Query("select count(1) from Seats where levelId=?1")
	public int findSeatCountByLevelId(int levelId);
	
	@Query("select count(1) from Seats")
	public int findTotalSeatCount();
	
/*	@Query("select s from Seats s left join FETCH s.holdingSeatMap hsm left join FETCH hsm.seatHold sh left join FETCH s.bookingSeatMap bsm "
			+ " where s.levelId=?1 and (sh.holdDate is null or sh.holdDate>'1901-01-01') and bsm.holdSeatMapId is null order by s.rowId, s.seatId")*/
	@Query("select s from Seats s left join FETCH s.bookingSeatMap bsm "
			+ " where s.levelId=?1 and bsm.holdSeatMapId is null order by s.rowId, s.seatId")
	public List<Seats> findAllNonBookedSeatsInLevel(int levelId);
	
	/*@Query("select s from Seats s left join FETCH s.holdingSeatMap hsm left join FETCH hsm.seatHold sh left join FETCH s.bookingSeatMap bsm "
			+ " where (sh.holdDate is null or sh.holdDate>'1901-01-01') and bsm.holdSeatMapId is null order by s.levelId,s.rowId,s.seatId")*/
	@Query("select s from Seats s left join FETCH s.bookingSeatMap bsm "
			+ " where bsm.holdSeatMapId is null order by s.rowId, s.seatId")
	public List<Seats> findAllNonBookedSeatsInAuditorium();
}
//select * from seats where seat_id in (select seat_id from holding_seat_map where hold_id in (select hold_id from seat_hold where datediff('SECONDS',CURRENT_TIMESTAMP(),hold_date)<300 ))
