package com.perigea.tracker.timesheet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Dipendente;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, String> {
	
	public Optional<Dipendente> findByCodicePersona(String codicePersona);

} 

