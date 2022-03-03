package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.StoricoAssegnazioneCentroCosto;
import com.perigea.tracker.timesheet.entity.keys.StoricoAssegnazioneCentroCostoKey;

@Repository
public interface StoricoAssegnazioneCentroCostoRepository extends JpaRepository<StoricoAssegnazioneCentroCosto, StoricoAssegnazioneCentroCostoKey> {

}
