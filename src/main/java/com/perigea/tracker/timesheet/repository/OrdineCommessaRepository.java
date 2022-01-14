package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.OrdineCommessa;
import com.perigea.tracker.timesheet.entity.keys.OrdineCommessaKey;

@Repository
public interface OrdineCommessaRepository extends JpaRepository<OrdineCommessa, OrdineCommessaKey> {

} 
