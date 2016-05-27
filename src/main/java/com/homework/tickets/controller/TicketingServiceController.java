package com.homework.tickets.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.homework.tickets.core.TicketingServiceImpl;
import com.homework.tickets.model.SeatHold;
import com.homework.tickets.request.TicketHoldRequest;
import com.homework.tickets.response.NumSeatsResponse;
import com.homework.tickets.response.ReserveSeatsResponse;

@RestController
public class TicketingServiceController {

	private static final Logger logger = LoggerFactory.getLogger(TicketingServiceController.class);
	@Autowired
	private TicketingServiceImpl service;

	@RequestMapping(value = "/numSeatsAvailable/{levelId}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<NumSeatsResponse> numSeatsAvailableInLevel(@PathVariable Integer levelId) {
		logger.debug("Entering numSeatsAvailableInLevel");
		Optional <Integer>o=Optional.empty();
		if(levelId!=null && levelId!=0){
			o=Optional.of(levelId);
		}
		Integer numSeats=service.numSeatsAvailable(o);
		NumSeatsResponse response=new NumSeatsResponse();
		response.setNumSeats(numSeats);
		logger.debug("Exiting numSeatsAvailableInLevel");
		return new ResponseEntity<NumSeatsResponse>(response,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/numSeatsAvailable", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<NumSeatsResponse> numSeatsAvailableInAuditorium() {
		Optional <Integer>o=Optional.empty();
		Integer numSeats=service.numSeatsAvailable(o);
		NumSeatsResponse response=new NumSeatsResponse();
		response.setNumSeats(numSeats);
		return new ResponseEntity<NumSeatsResponse>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/reserveSeats/{holdId}/{email:.+}", method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ReserveSeatsResponse> reserveSeats(@PathVariable Integer holdId,@PathVariable String email) {
		DataValidationHelper.checkEmptyValueForIntegerAndString(email,"Email Required");
		ReserveSeatsResponse response= new ReserveSeatsResponse();
		response.setConfirmationNumber(service.reserveSeats(holdId, email));
		return new ResponseEntity<ReserveSeatsResponse>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/findAndHoldSeats", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SeatHold> findAndHoldSeats(@RequestBody TicketHoldRequest request) {
		Optional<Integer> minLevel;
		DataValidationHelper.checkEmptyValueForIntegerAndString(request.getNumSeats(),"numSeats canot be empty or 0");
		DataValidationHelper.checkEmptyValueForIntegerAndString(request.getEmail(),"Email required");
		if(request.getMinLevel()==null ||request.getMinLevel()==0 ){
			minLevel=Optional.empty();
		}else{
			minLevel=Optional.of(request.getMinLevel());
		}
		Optional<Integer> maxLevel;
		if(request.getMaxLevel()==null ||request.getMaxLevel()==0 ){
			maxLevel=Optional.empty();
		}else{
			maxLevel=Optional.of(request.getMaxLevel());
		}
		return new ResponseEntity<SeatHold>(service.findAndHoldSeats(request.getNumSeats(), minLevel, maxLevel, request.getEmail()), HttpStatus.OK);
	}
}
