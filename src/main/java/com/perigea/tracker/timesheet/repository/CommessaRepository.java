package com.perigea.tracker.timesheet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Commessa;

@Repository
public interface CommessaRepository extends JpaRepository<Commessa, String> {
	
	public Optional<Commessa> findByCodiceCommessa(String codiceCommessa);

} 
