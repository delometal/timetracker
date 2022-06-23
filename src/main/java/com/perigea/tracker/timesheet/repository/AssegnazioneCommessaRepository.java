package com.perigea.tracker.timesheet.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.PersonaleCommessa;
import com.perigea.tracker.timesheet.entity.keys.DipendenteCommessaKey;

@Repository
public interface AssegnazioneCommessaRepository extends JpaRepository<PersonaleCommessa, DipendenteCommessaKey>, JpaSpecificationExecutor<PersonaleCommessa> {
	
	public List<PersonaleCommessa> findAll(Specification<PersonaleCommessa> filter);
} 
