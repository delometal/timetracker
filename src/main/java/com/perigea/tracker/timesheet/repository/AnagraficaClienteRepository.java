package com.perigea.tracker.timesheet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.AnagraficaCliente;

@Repository
public interface AnagraficaClienteRepository extends JpaRepository<AnagraficaCliente, String> {

	public Optional<AnagraficaCliente> findByPartitaIva(String partitaIva);
	
}
