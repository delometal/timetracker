package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Ruoli;
import com.perigea.tracker.timesheet.enumerator.RuoloType;

@Repository
public interface RuoliRepository extends JpaRepository<Ruoli,Long> {
	
	public Ruoli findByRuoloType(String string);
	

} 