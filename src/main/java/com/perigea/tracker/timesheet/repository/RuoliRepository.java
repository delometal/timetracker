package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.enums.RuoloType;

@Repository
public interface RuoliRepository extends JpaRepository<Ruolo, RuoloType> {
	
	public Ruolo findByTipo(RuoloType tipo);

} 