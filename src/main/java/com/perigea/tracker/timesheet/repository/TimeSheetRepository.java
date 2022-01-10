package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.TimeSheet;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.RelazioneIdTimeSheetKey;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, RelazioneIdTimeSheetKey> {
	
	public TimeSheet findByUtenteTimeSheet(Utente utenteTimeSheet);

	 @Query(value = "some query", nativeQuery = true)
	    Long findAmountOfFriends(@Param("userId") Long userId);
} 
