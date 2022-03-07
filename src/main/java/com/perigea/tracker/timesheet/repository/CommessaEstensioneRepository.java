package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perigea.tracker.timesheet.entity.CommessaEstensione;
import com.perigea.tracker.timesheet.entity.keys.CommessaEstensioneKey;

public interface CommessaEstensioneRepository extends JpaRepository<CommessaEstensione, CommessaEstensioneKey> {

}
