package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.TimesheetKey;

@Repository
public interface TimeSheetRepository extends JpaRepository<Timesheet, TimesheetKey> {
	
	public Timesheet findByUtenteTimesheet(Utente utenteTimesheet);

} 
