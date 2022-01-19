package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.keys.TimesheetMensileKey;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, TimesheetMensileKey> {
	
}
