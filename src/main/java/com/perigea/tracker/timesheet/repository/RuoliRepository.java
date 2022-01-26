package com.perigea.tracker.timesheet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.enums.RuoloType;

@Repository
public interface RuoliRepository extends JpaRepository<Ruolo, RuoloType> {
	
	public Optional<Ruolo> findByTipo(RuoloType tipo);

} 