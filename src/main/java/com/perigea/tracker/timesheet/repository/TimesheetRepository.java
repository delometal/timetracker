package com.perigea.tracker.timesheet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.keys.TimesheetMensileKey;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, TimesheetMensileKey>, JpaSpecificationExecutor<Timesheet> {
	
	public List<Timesheet> findAllByIdAnnoAndIdMese(Integer anno, Integer mese);
}
