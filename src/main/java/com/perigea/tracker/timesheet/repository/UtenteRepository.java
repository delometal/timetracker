package com.perigea.tracker.timesheet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.commons.enums.AnagraficaType;
import com.perigea.tracker.timesheet.entity.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, String>, JpaSpecificationExecutor<Utente> {

	public Optional<Utente> findByCodicePersona(String codicePersona);
	public Optional<Utente> findByCodicePersonaAndTipo(String codicePersona, AnagraficaType tipo);
	public List<Utente> findAllByNomeAndCognome(String nome, String cognome);
	

	@Query(value = "SELECT u FROM Utente u WHERE u.tipo = :tipo")
	public List<Utente> findAllOfType(@Param("tipo") AnagraficaType tipo);

}
