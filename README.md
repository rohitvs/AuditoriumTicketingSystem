# AuditoriumTicketingSystem

More detailed description of the application can be found in README.doc in the root folder of the project

# Running the application

Go to the Project Root Directory and run the below command

1)	mvn package && java -jar target/AuditoriumTicketingSystem-0.0.1-SNAPSHOT.jar

This Command will compile the code, run the tests and start tomcat server in port 8080 where the application is deployed. The backend system is In Memory H2 Database.

2)	If you just want to build and run the test then just run mvn clean install

3)	The application exposes a REST API which has 3 methods detailed below.


# REST API Details:
You can use POSTMAN to send the HTTP Requests 
1)	numSeatsAvailable without level -GET REQUEST-  http://localhost:8080/numSeatsAvailable/
This will bring back the total number of available seats in the auditorium

1.1	numSeatsAvailable with level  -GET REQUEST- http://localhost:8080/numSeatsAvailable/1/

This will bring back the total number of available seats in level 1. If we change the path param to 2, 3 or 4 it will bring back the total number of available seats in the corresponding levels

2)	findAndHoldSeats -POST http://localhost:8080/findAndHoldSeats 
The content type MUST be set to application/json for this to work
Request body:

{"email":"rohit_vs@yahoo.com","numSeats": "2","minLevel":"1","maxLevel":"3"}
	This will hold 2 seats for a user between levels 1 and 3 if its available
	
3)	reserveSeats – PUT - http://localhost:8080/reserveSeats/1/rohit_vs@yahoo.com

# Notes

1)	The application exposes a REST API which has four methods(detailed above).
2)	The backend system is In Memory H2 Database.

3)	There are 2 profiles for the data setup(Dev and Test). When the tests run, the test profile is utilized. The Application connects to H2 In Memory Database which has the tables and data that the application will utilize. For the tests, the tables will be pre-loaded with data in all tables.

4)	When the application starts in dev profile, all the tables get created. Only AUDITORIUM_METADATA, SEATS AND PROPERTIES Table will have data. Other tables will be empty. As and when the seats get hold or booked, corresponding tables will get created

5)	When the tests run, it starts the app server in a random available port and sets up data in H2  in-memory database. All of the test data is available in data-test.sql. 

6)	AuditoriumTicketingSystemApplicationTests class runs the tests for numSeats and Exception scenarios
7)	TicketingSystemSimulatorTests runs the tests for Holding and booking. It brings the database up in a particular state. There is just one test method in this class but it does a series of actions like holding, then booking, then hold again. Basically it simulates user behavior and then runs assert statements to make sure the output is as expected.



