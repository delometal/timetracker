package com.perigea.tracker.timesheet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Festivita;

@Repository
public interface FestivitaRepository extends JpaRepository<Festivita, Integer>, JpaSpecificationExecutor<Festivita> {

	public Optional<Festivita> findByNomeFestivo(String nomeFestivo);
}
