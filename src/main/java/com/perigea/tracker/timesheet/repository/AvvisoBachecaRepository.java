package com.perigea.tracker.timesheet.repository;

import com.perigea.tracker.timesheet.entity.AvvisoBacheca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvvisoBachecaRepository extends JpaRepository<AvvisoBacheca, Long> {
}
