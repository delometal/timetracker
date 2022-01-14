package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.DipendenteCommessa;
import com.perigea.tracker.timesheet.entity.keys.DipendenteCommessaKey;

@Repository
public interface DipendenteCommessaRepository extends JpaRepository<DipendenteCommessa, DipendenteCommessaKey> {

} 
