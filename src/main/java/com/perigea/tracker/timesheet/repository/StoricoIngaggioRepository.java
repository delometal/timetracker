package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.StoricoIngaggio;
import com.perigea.tracker.timesheet.entity.keys.StoricoIngaggioKey;

@Repository
public interface StoricoIngaggioRepository extends JpaRepository<StoricoIngaggio, StoricoIngaggioKey> {

}
