package com.perigea.tracker.timesheet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.CommessaFatturabile;

@Repository
public interface CommessaFatturabileRepository extends JpaRepository<CommessaFatturabile, String>, JpaSpecificationExecutor<CommessaFatturabile> {

	public Optional<CommessaFatturabile> findByCodiceCommessa(String codiceCommessa);
	
	public List<CommessaFatturabile> findAll(Specification<CommessaFatturabile> filter);

} 