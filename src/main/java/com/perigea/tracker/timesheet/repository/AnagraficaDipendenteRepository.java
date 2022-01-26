package com.perigea.tracker.timesheet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.AnagraficaDipendente;

@Repository
public interface AnagraficaDipendenteRepository extends JpaRepository<AnagraficaDipendente, String> {
	
	public Optional<AnagraficaDipendente> findByCodicePersona(String codicePersona);

} 

