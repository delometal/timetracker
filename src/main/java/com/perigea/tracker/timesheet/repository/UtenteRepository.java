package com.perigea.tracker.timesheet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Ruoli;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.enumerator.RuoloType;

@Repository
public interface UtenteRepository extends JpaRepository<Utente,Long> {
	
	public Utente findByCodicePersona(String codicePersona);
	
	
	//public List<UtenteEntity> findByRuoloType(RuoloType ruoloType);
	

} 
