package com.perigea.tracker.timesheet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Anagrafica;

@Repository
public interface AnagraficaRepository extends JpaRepository<Anagrafica, String> {
	
	public Optional<Anagrafica> findByCodicePersona(String codicePersona);

	public Optional<Anagrafica> findByMailAziendale(String email);

} 

