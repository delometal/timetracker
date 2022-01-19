package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.TimesheetEntry;
import com.perigea.tracker.timesheet.entity.keys.TimesheetEntryKey;

@Repository
public interface TimesheetDataRepository extends JpaRepository<TimesheetEntry, TimesheetEntryKey> {
	


} 
