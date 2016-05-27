package com.homework.tickets.core;

import java.util.List;

public interface SeatAssigner <A,B>{

	public A assignSeatsInSameLevel(List<B> obj, int numSeats);
}
