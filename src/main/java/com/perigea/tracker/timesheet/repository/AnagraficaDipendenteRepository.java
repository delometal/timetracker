package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.AnagraficaDipendente;

@Repository
public interface AnagraficaDipendenteRepository extends JpaRepository<AnagraficaDipendente,Long> {
	
	public AnagraficaDipendente findByCodicePersona(String codicePersona);

} 

