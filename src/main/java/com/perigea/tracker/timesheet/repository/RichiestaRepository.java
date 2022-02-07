package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Richiesta;

@Repository
public interface RichiestaRepository extends JpaRepository<Richiesta, Integer> {
	
} 