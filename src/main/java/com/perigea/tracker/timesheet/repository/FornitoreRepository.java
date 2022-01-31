package com.perigea.tracker.timesheet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Fornitore;

@Repository
public interface FornitoreRepository extends JpaRepository<Fornitore, String> {

	public Optional<Fornitore> findByPartitaIva(String partitaIva);
	
}
