package com.homework.tickets.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.homework.tickets.model.Seats;

@Component
public class GreedySeatAssigner implements SeatAssigner<List<Seats>,Seats>{
	/**
	 * This method loops through the list of sorted available seats and tries to
	 * find the first available continuous hole of size=<numSeats>. If it is
	 * unable to find continuous seats it assigns the first <numSeats> available
	 * seats from the List<seats> passed as agrument to the method
	 * 
	 * @param seats
	 * @param numSeats
	 * @return
	 */
	public List<Seats> assignSeatsInSameLevel(List<Seats> seats, int numSeats){
		int rowId = 0;
		int tempRowId = 0;
		int seatId=0;
		int tempSeatId=0;
		int seatsFound = 0;
		List<Seats> assignedSeats= new ArrayList<Seats>();
		if(seats.size()<numSeats){
			return assignedSeats;// return empty
		}
		for (int j = 0; j < seats.size(); j++) {
			Seats seat = seats.get(j);
			rowId = seat.getRowId();
			seatId=seat.getSeatId();
			if (j > 0 && tempRowId != rowId) {// this means row is changing and we still haven't found the required number of seats
				seatsFound=0;
				assignedSeats.clear();//clear all the seats found
				assignedSeats.add(seats.get(j));
				seatsFound++;
			} else {
				if(assignedSeats.size()==0 || seatId-tempSeatId==1){
					assignedSeats.add(seats.get(j));
					seatsFound++;
				}else{
					assignedSeats.clear();
					seatsFound=0;
				}
				if (seatsFound == numSeats) {
					break;
				}
			}
			tempRowId = rowId;
			tempSeatId=seatId;
		}
		// This means that it couldn't find continuous seats. Hence assign the
		// first numSeats available seats from the sorted seat list 
		if(assignedSeats.isEmpty()){
			for(int i=0;i<numSeats;i++){
				assignedSeats.add(seats.get(i));
			}
		}
		return assignedSeats;
	}

}
