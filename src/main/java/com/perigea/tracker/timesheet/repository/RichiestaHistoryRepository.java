package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.RichiestaHistory;

@Repository
public interface RichiestaHistoryRepository extends JpaRepository<RichiestaHistory, Long>, JpaSpecificationExecutor<RichiestaHistory> {
	
} 