package com.perigea.tracker.timesheet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Fornitore;

@Repository
public interface FornitoreRepository extends JpaRepository<Fornitore, String>, JpaSpecificationExecutor<Fornitore> {

	public Optional<Fornitore> findByPartitaIva(String partitaIva);
	
}
