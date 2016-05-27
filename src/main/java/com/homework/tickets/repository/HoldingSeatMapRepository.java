package com.homework.tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homework.tickets.model.HoldingSeatMap;

public interface HoldingSeatMapRepository extends JpaRepository<HoldingSeatMap, Integer>{

}
