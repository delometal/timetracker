package com.perigea.tracker.timesheet.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.enums.ApprovalStatus;
import com.perigea.tracker.commons.enums.StatoUtenteType;
import com.perigea.tracker.commons.exception.PersistenceException;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.TimesheetMensileKey;

@Repository
public class ApplicationDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public Integer updateTimesheetStatus(TimesheetMensileKey key, ApprovalStatus newStatus) {
		try {
			Predicate[] predicates = new Predicate[3];
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaUpdate<Timesheet> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Timesheet.class);
			Root<Timesheet> timesheet = criteriaUpdate.from(Timesheet.class);
			predicates[0] = criteriaBuilder.equal(timesheet.get("id").<String>get("anno"), key.getAnno());
			predicates[1] = criteriaBuilder.equal(timesheet.get("id").<String>get("mese"), key.getMese());
			predicates[2] = criteriaBuilder.equal(timesheet.get("id").<String>get("codicePersona"),
					key.getCodicePersona());

			criteriaUpdate.set("statoRichiesta", newStatus);
			criteriaUpdate.where(predicates);

			return entityManager.createQuery(criteriaUpdate).executeUpdate();
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@Transactional
	public Integer updateUserStatus(String codicePersona, StatoUtenteType status) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaUpdate<Utente> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Utente.class);
			Root<Utente> user = criteriaUpdate.from(Utente.class);

			criteriaUpdate.set("stato", status);
			criteriaUpdate.where(criteriaBuilder.equal(user.<String>get("codicePersona"), codicePersona));

			return entityManager.createQuery(criteriaUpdate).executeUpdate();
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	
	@Transactional
	public Integer updateUserPassword(String codicePersona, String password) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaUpdate<Utente> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Utente.class);
			Root<Utente> user = criteriaUpdate.from(Utente.class);

			criteriaUpdate.set("password", password);
			criteriaUpdate.where(criteriaBuilder.equal(user.<String>get("codicePersona"), codicePersona));

			return entityManager.createQuery(criteriaUpdate).executeUpdate();
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

}