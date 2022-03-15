package com.perigea.tracker.timesheet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Dipendente;
import com.perigea.tracker.timesheet.entity.Personale;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, String>, JpaSpecificationExecutor<Dipendente> {
	
	public Optional<Dipendente> findByCodicePersona(String codicePersona);

	public List<Dipendente> findAll(Specification<Dipendente> filter);
	
	public List<Personale> findAllByResponsabile(Personale responsabile);
} 

