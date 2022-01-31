package com.perigea.tracker.timesheet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Contatto;

@Repository
public interface ContattoRepository extends JpaRepository<Contatto, Long> {

	public Optional<Contatto> findByMailAziendale(String email);
	
	public Optional<Contatto> findByMailPrivata(String email);

	public Optional<Contatto> findByCodiceFiscale(String codiceFiscale);

	public Optional<Contatto> findByCellulare(String cellulare);

} 
