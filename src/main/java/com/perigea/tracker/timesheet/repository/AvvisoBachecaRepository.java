package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.AvvisoBacheca;

@Repository
public interface AvvisoBachecaRepository extends JpaRepository<AvvisoBacheca, Long> {
}
