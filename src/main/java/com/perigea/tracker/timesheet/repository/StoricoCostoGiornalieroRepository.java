package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.StoricoCostoGiornaliero;
import com.perigea.tracker.timesheet.entity.keys.StoricoGiornalieroKey;

@Repository
public interface StoricoCostoGiornalieroRepository extends JpaRepository<StoricoCostoGiornaliero, StoricoGiornalieroKey> {

}
