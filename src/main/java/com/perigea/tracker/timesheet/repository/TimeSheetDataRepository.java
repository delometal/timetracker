package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.TimesheetData;
import com.perigea.tracker.timesheet.entity.keys.TimesheetDataKey;

@Repository
public interface TimeSheetDataRepository extends JpaRepository<TimesheetData, TimesheetDataKey> {
	


} 
