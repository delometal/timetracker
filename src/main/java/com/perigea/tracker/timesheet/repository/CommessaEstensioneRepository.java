package com.perigea.tracker.timesheet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.CommessaEstensione;
import com.perigea.tracker.timesheet.entity.keys.CommessaEstensioneKey;

@Repository
public interface CommessaEstensioneRepository extends JpaRepository<CommessaEstensione, CommessaEstensioneKey> {

	@Query(value = "SELECT * FROM tracker.estensione_commessa WHERE codice_commessa = ?1",
			nativeQuery = true)
	public List<CommessaEstensione> findAllByCodiceCommessa(String codiceCommessa);
	
}
