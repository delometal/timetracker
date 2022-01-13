package com.perigea.tracker.timesheet.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Festivita;

@Repository
public interface FestivitaRepository extends JpaRepository<Festivita, Integer> {

	public Festivita findByNomeFestivo(String nomeFestivo);
}
