package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.StoricoRal;
import com.perigea.tracker.timesheet.entity.keys.StoricoRalKey;

@Repository
public interface StoricoRalRepository extends JpaRepository<StoricoRal, StoricoRalKey>{

}
