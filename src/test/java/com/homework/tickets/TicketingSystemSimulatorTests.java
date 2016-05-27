package com.homework.tickets;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.homework.tickets.model.SeatHold;
import com.homework.tickets.repository.SeatHoldRepository;
import   com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AuditoriumTicketingSystemApplication.class)
@WebIntegrationTest(randomPort = true)
@ActiveProfiles("test")
/**
 * This test class in an integration test for the rest apis for numSeatsAvaiable, findAndHoldSeats and reserveSeats
 * The test starts the tomcat server and loads test data provided in data-test.sql file.
 * The test data assumes 
 * 1) there are 4 levels in the auditorium
 * 2) each level has 2 rows
 * 3) Each row has 5 seats
 * 4) Level 1 has 7 seats in active hold status
 * 5) Level 2 has 6 seats in booked status
 * 6) Level 3 has 3 seats in booked status
 * 7) Level 4 has no booked or in hold seats(All available)
 * @author Rohit Vardarajan
 *
 */
public class TicketingSystemSimulatorTests {

	@Value("${local.server.port}")   
    int port;
	@Autowired private SeatHoldRepository seatHoldRepo;
	
	@Before
    public void setUp() {
        RestAssured.port = port;
    }
	/**
	 * This test will perform a series of user actions like holding seats and booking seats.
	 * After each action the asserts confirm expected result
	 */
	@Test
    public void testFindAndHoldSeatsAndReserveSeats() {
		//Total Available seats in the beginning=24
		//First hold 2 seats for one user
    	SeatHold seatHold=given()
    	    	.contentType("application/json").
    	    	body("{\"email\":\"rohit_vs@yahoo.com\",\"numSeats\":\"2\",\"minLevel\":\"1\",\"maxLevel\":\"3\"}").
    	        when().
    	        post("/findAndHoldSeats").as(SeatHold.class);
    	
    	Assert.assertEquals(seatHold.getHoldingSeatMap().size(),2);
    	Assert.assertEquals(seatHold.getHoldingSeatMap().get(0).getSeats().getSeatId(),new Integer(7));
    	Assert.assertEquals(seatHold.getHoldingSeatMap().get(1).getSeats().getSeatId(),new Integer(8));
    	Assert.assertEquals(seatHold.getHoldingSeatMap().get(1).getSeats().getLevelId(),new Integer(1));
    	
    	//Confirm that now there are 22 available seats
    	given().contentType("application/json").
		when().get("/numSeatsAvailable/").
		then().body("numSeats", equalTo(22))
		.statusCode(200);
    	
    	//Confirm that now there is 1 available seat in level 1
    	given().contentType("application/json").
		when().get("/numSeatsAvailable/1").
		then().body("numSeats", equalTo(1))
		.statusCode(200);
    	
    	//Book the seats that was held above
    	given()
    	.contentType("application/json").
        when().
        put("/reserveSeats/"+seatHold.getHoldId()+"/rohit_vs@yahoo.com").then().statusCode(200).body(("confirmationNumber"),any(String.class));
    	
    	//Confirm that now there are 1 available seats
    	given().contentType("application/json").
		when().get("/numSeatsAvailable/").
		then().body("numSeats", equalTo(22))
		.statusCode(200);
    	SeatHold expiredSeatHold=seatHoldRepo.findOne(seatHold.getHoldId());
    	Assert.assertEquals(expiredSeatHold.getHoldDate(), new Timestamp(new Date("01/01/1901").getTime()));
    	//Hold 3 Seats between level 2 and 3
    	seatHold=given()
    	    	.contentType("application/json").
    	    	body("{\"email\":\"rohit_vs@yahoo.com\",\"numSeats\":\"3\",\"minLevel\":\"2\",\"maxLevel\":\"3\"}").
    	        when().
    	        post("/findAndHoldSeats").as(SeatHold.class);
    	
    	Assert.assertEquals(seatHold.getHoldingSeatMap().size(),3);
    	Assert.assertEquals(seatHold.getHoldingSeatMap().get(0).getSeats().getSeatId(),new Integer(12));
    	Assert.assertEquals(seatHold.getHoldingSeatMap().get(1).getSeats().getSeatId(),new Integer(13));
    	Assert.assertEquals(seatHold.getHoldingSeatMap().get(2).getSeats().getSeatId(),new Integer(14));
    	Assert.assertEquals(seatHold.getHoldingSeatMap().get(0).getSeats().getLevelId(),new Integer(2));
    	Assert.assertEquals(seatHold.getHoldingSeatMap().get(1).getSeats().getLevelId(),new Integer(2));
    	Assert.assertEquals(seatHold.getHoldingSeatMap().get(2).getSeats().getLevelId(),new Integer(2));
    	
    	//Confirm that now there are 19 available seats
    	given().contentType("application/json").
		when().get("/numSeatsAvailable/").
		then().body("numSeats", equalTo(19))
		.statusCode(200);
    	
    	//Confirm that now there are 4 available seats in level 2
    	given().contentType("application/json").
		when().get("/numSeatsAvailable/2").
		then().body("numSeats", equalTo(1))
		.statusCode(200);
    	
    	//Hold 2 Seats between level 1 and 2. Expected seats to be allocated is seatid 4 from level 1 and seatId 18 in level 2
    	seatHold=given()
    	    	.contentType("application/json").
    	    	body("{\"email\":\"rohit_vs@yahoo.com\",\"numSeats\":\"2\",\"minLevel\":\"1\",\"maxLevel\":\"2\"}").
    	        when().
    	        post("/findAndHoldSeats").as(SeatHold.class);
    	
    	Assert.assertEquals(seatHold.getHoldingSeatMap().size(),2);
    	Assert.assertEquals(seatHold.getHoldingSeatMap().get(0).getSeats().getSeatId(),new Integer(4));
    	Assert.assertEquals(seatHold.getHoldingSeatMap().get(1).getSeats().getSeatId(),new Integer(18));
    	Assert.assertEquals(seatHold.getHoldingSeatMap().get(0).getSeats().getLevelId(),new Integer(1));
    	Assert.assertEquals(seatHold.getHoldingSeatMap().get(1).getSeats().getLevelId(),new Integer(2));
    	
    	//Seats in level 2 are now all either in hold status or booked. Lets try holding more seats in level 2.
    	//Hold 2 Seats between level 1 and 2. Expected seats to be allocated is seatid 4 from level 1 and seatId 18 in level 2
    	given()
    	    	.contentType("application/json").
    	    	body("{\"email\":\"rohit_vs@yahoo.com\",\"numSeats\":\"2\",\"maxLevel\":\"2\"}").
    	        when().
    	        post("/findAndHoldSeats").then().statusCode(404).body("message",equalTo("There are no more seats available at this moment in the levels you requested."));
    	
    	given()
    	.contentType("application/json").
    	body("{\"email\":\"rohit_vs@yahoo.com\",\"numSeats\":\"100\",\"minLevel\":\"1\",\"maxLevel\":\"4\"}").
        when().
        post("/findAndHoldSeats").then().statusCode(404).body("message",equalTo("There are no more seats available at this moment in the levels you requested."));
    	
    	
    }
    
   
}
