package com.homework.tickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homework.tickets.model.AuditoriumMetaData;

public interface AuditoriumMetaDataRepository extends JpaRepository<AuditoriumMetaData, Integer>{

	public List<AuditoriumMetaData> findByLevelId(Integer levelId);
}
