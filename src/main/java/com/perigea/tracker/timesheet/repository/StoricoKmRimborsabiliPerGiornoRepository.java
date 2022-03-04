package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.StoricoKmRimborsabiliPerGiorno;
import com.perigea.tracker.timesheet.entity.keys.StoricoKmRimborsabiliPerGiornoKey;

@Repository
public interface StoricoKmRimborsabiliPerGiornoRepository extends JpaRepository<StoricoKmRimborsabiliPerGiorno, StoricoKmRimborsabiliPerGiornoKey> {

}
