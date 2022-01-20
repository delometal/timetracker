package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.NotaSpese;
import com.perigea.tracker.timesheet.entity.keys.NotaSpeseKey;

@Repository
public interface NotaSpeseRepository extends JpaRepository<NotaSpese, Long> {
	
	public NotaSpese findById (NotaSpeseKey id);
}

