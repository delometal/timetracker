package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.StoricoPremio;
import com.perigea.tracker.timesheet.entity.keys.StoricoPremioKey;

@Repository
public interface StoricoPremioRepository extends JpaRepository<StoricoPremio, StoricoPremioKey> {

}
