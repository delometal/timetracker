package com.perigea.tracker.timesheet.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.CentroDiCosto;

@Repository
public interface CentroDiCostoRepository extends JpaRepository<CentroDiCosto, String>, JpaSpecificationExecutor<CentroDiCosto> {
	
//	public List<CentroDiCosto> findByCodiceCentroDiCostoContainsIgnoreCaseOrDescrizioneContainsIgnoreCase(String codiceCentroDiCosto, String descrizione);
	public List<CentroDiCosto> findAll(Specification<CentroDiCosto> filter);

}
