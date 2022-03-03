package com.perigea.tracker.timesheet.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.StoricoRimborsiKm;
import com.perigea.tracker.timesheet.entity.keys.StoricoRimborsiKmKey;

@Repository
public interface StoricoRimborsiKmRepository extends JpaRepository<StoricoRimborsiKm, StoricoRimborsiKmKey>{

}
