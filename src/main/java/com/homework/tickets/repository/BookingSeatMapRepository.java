package com.homework.tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homework.tickets.model.BookingSeatMap;

public interface BookingSeatMapRepository extends JpaRepository<BookingSeatMap, Integer>{

}
