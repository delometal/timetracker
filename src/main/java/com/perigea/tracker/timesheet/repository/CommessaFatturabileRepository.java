package com.perigea.tracker.timesheet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.CommessaFatturabile;

@Repository
public interface CommessaFatturabileRepository extends JpaRepository<CommessaFatturabile, String> {

	public Optional<CommessaFatturabile> findByCodiceCommessa(String codiceCommessa);

} 