--CREATE TABLE users(user_id INT PRIMARY KEY , email VARCHAR(255))
CREATE TABLE seat_hold(hold_id INT auto_increment PRIMARY KEY, email VARCHAR(50),hold_date TIMESTAMP)

CREATE TABLE booking(booking_id INT auto_increment PRIMARY KEY, email VARCHAR(50) ,booking_date TIMESTAMP)
CREATE TABLE holding_seat_map(hold_seat_map_id INT auto_increment PRIMARY KEY,hold_id INT ,seat_id INT )
CREATE TABLE booking_seat_map(booking_seat_map_id INT auto_increment PRIMARY KEY,booking_id INT ,seat_id INT )
CREATE TABLE SEATS(seat_id INT auto_increment PRIMARY KEY,level_id INT , row_id INT, seat_num INT)
CREATE TABLE AUDITORIUM_METADATA(level_id INT auto_increment PRIMARY KEY,level_name varchar(50),price DOUBLE, total_rows INT, SEATS_IN_ROW INT)
CREATE TABLE PROPERTIES(KEY_NAME VARCHAR(255),VALUE VARCHAR(255))

--ALTER TABLE seat_hold ADD CONSTRAINT fk_users_seathold_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
--
--ALTER TABLE booking ADD CONSTRAINT fk_users_booking_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
ALTER TABLE holding_seat_map ADD CONSTRAINT fk_seat_hold_h_seat_map_hold_id FOREIGN KEY (hold_id) REFERENCES seat_hold(hold_id)
ALTER TABLE holding_seat_map ADD CONSTRAINT fk_seats_h_seat_map_hold_id FOREIGN KEY (seat_id) REFERENCES seats(seat_id)

ALTER TABLE booking_seat_map ADD CONSTRAINT fk_seats_hold_b_seat_map_booking_id FOREIGN KEY (booking_id) REFERENCES booking(booking_id)
ALTER TABLE booking_seat_map ADD CONSTRAINT fk_seats_b_seat_map_seat_id FOREIGN KEY (seat_id) REFERENCES seats(seat_id)

ALTER TABLE SEATS ADD CONSTRAINT fk_auditorium_metadata_seats_level_id FOREIGN KEY (level_id) REFERENCES auditorium_metadata(level_id)

