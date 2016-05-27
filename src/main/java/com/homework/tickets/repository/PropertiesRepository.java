package com.homework.tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homework.tickets.model.Properties;

public interface PropertiesRepository extends JpaRepository<Properties, String> {

}
