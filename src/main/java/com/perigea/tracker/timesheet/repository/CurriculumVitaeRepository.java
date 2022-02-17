package com.perigea.tracker.timesheet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.CurriculumVitae;

@Repository
public interface CurriculumVitaeRepository extends JpaRepository<CurriculumVitae, String>, JpaSpecificationExecutor<CurriculumVitae> {
	
	public Optional<CurriculumVitae> findByCodicePersona(String codicePersona);

} 

