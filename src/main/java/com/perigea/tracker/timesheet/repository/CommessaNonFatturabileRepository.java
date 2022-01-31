package com.perigea.tracker.timesheet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;

@Repository
public interface CommessaNonFatturabileRepository extends JpaRepository<CommessaNonFatturabile, String> {
	
	public Optional<CommessaNonFatturabile> findByCodiceCommessa(String codiceCommessa);

} 