package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.StoricoContrattoType;
import com.perigea.tracker.timesheet.entity.keys.StoricoLivelloContrattualeKey;

@Repository
public interface StoricoContrattoTypeRepository extends JpaRepository<StoricoContrattoType, StoricoLivelloContrattualeKey> {

}
