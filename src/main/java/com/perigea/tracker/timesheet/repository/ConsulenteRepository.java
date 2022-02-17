package com.perigea.tracker.timesheet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Consulente;

@Repository
public interface ConsulenteRepository extends JpaRepository<Consulente, String>, JpaSpecificationExecutor<Consulente> {
	
	public Optional<Consulente> findByCodicePersona(String codicePersona);
	public Optional<Consulente> findByPartitaIva(String partitaIva);

} 

