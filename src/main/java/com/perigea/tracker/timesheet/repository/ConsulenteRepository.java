package com.perigea.tracker.timesheet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Consulente;
import com.perigea.tracker.timesheet.entity.Personale;

@Repository
public interface ConsulenteRepository extends JpaRepository<Consulente, String>, JpaSpecificationExecutor<Consulente> {
	
	public Optional<Consulente> findByCodicePersona(String codicePersona);
	public Optional<Consulente> findByPartitaIva(String partitaIva);
	
	public List<Personale> findAllByResponsabile(Personale responsabile);
	
	public List<Consulente> findAll(Specification<Consulente> filter);
	
	
} 

