package com.perigea.tracker.timesheet.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.TimesheetEntry;
import com.perigea.tracker.timesheet.entity.keys.TimesheetEntryKey;

@Repository
public interface TimesheetDataRepository
		extends JpaRepository<TimesheetEntry, TimesheetEntryKey>, JpaSpecificationExecutor<TimesheetEntry> {

	public List<TimesheetEntry> findAllByIdGiornoAndIdMeseAndIdAnnoAndIdCodicePersona(Integer giorno, Integer mese,
			Integer anno, String codicePersona);

	public List<TimesheetEntry> findAll(Specification<TimesheetEntry> filter);
}
