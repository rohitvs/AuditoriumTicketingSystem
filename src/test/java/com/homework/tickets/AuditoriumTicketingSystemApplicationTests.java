package com.homework.tickets;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
public class AuditoriumTicketingSystemApplicationTests {

	@Value("${local.server.port}")   // 6
    int port;
	
	@Before
    public void setUp() {
        RestAssured.port = port;
    }
	
	/**
	 * Test numSeatsAvailable when not path variable is passed
	 */
    @Test
    public void testNumSeatsAvailableWithoutLevel() {
		given().contentType("application/json").
		when().get("/numSeatsAvailable/").
		then().body("numSeats", equalTo(24))
		.statusCode(200);
    }

    /**
	 * Test numSeatsAvailableForEachLevel when not path variable is passed
	 */
    @Test
    public void testNumSeatsAvailableInEachLevel() {
        given().contentType("application/json").when().get("/numSeatsAvailable/1").then().body("numSeats", equalTo(3)).statusCode(200);
        given().contentType("application/json").when().get("/numSeatsAvailable/2").then().body("numSeats", equalTo(4)).statusCode(200);
        given().contentType("application/json").when().get("/numSeatsAvailable/3").then().body("numSeats", equalTo(7)).statusCode(200);
        given().contentType("application/json").when().get("/numSeatsAvailable/4").then().body("numSeats", equalTo(10)).statusCode(200);
  
    }
    
    @Test
    public void testFindAndHoldSeatsRequiredArguments() {
    	given()
    	    	.contentType("application/json").
    	    	body("{\"numSeats\":\"2\",\"minLevel\":\"1\",\"maxLevel\":\"3\"}").
    	        when().
    	        post("/findAndHoldSeats").then().statusCode(500).body("message", equalTo("Email required"));
    	
    	given()
    	.contentType("application/json").
    	body("{\"email\":\"rohit_vs@yahoo.com\",\"minLevel\":\"1\",\"maxLevel\":\"3\"}").
        when().
        post("/findAndHoldSeats").then().statusCode(500).body("message", equalTo("numSeats canot be empty or 0"));
    }
    
    @Test
    /**
     * Tests for an invalid hold id when trying to reserve seats
     */
    public void testInvalidHoldId(){
    	given()
    	.contentType("application/json").
        when().
        put("/reserveSeats/99/rohit.vardarajan@finra.org").then().statusCode(404).body("message", equalTo("This Ticket Hold Id coudnot be found"));
    }
    
    @Test
    /**
     * Tests for an expired hold id when trying to reserve seats
     */
    public void testHoldExpired(){
    	//Book the seats that was held above
    	given()
    	.contentType("application/json").
        when().
        put("/reserveSeats/4/test@test.com").then().statusCode(406).body("message",equalTo("This Ticket Hold Id has already expired"));
    }
    
    @Test
    /**
     * Tests for an expired hold id when trying to reserve seats
     */
    public void testUnAuthorizedSave(){
    	//Book the seats that was held above
    	given()
    	.contentType("application/json").
        when().
        put("/reserveSeats/1/test@test.com").then().statusCode(401).body("message",equalTo("You are unauthorized to save this booking!"));
    }
    
}
