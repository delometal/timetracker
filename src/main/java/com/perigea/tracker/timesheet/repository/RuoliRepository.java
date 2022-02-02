package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.commons.enums.RuoloType;
import com.perigea.tracker.timesheet.entity.Ruolo;

@Repository
public interface RuoliRepository extends JpaRepository<Ruolo, RuoloType> {
	
} 