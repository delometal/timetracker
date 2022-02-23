package com.perigea.tracker.timesheet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String>, JpaSpecificationExecutor<Cliente> {

	public Optional<Cliente> findByPartitaIva(String partitaIva);

	public Optional<Cliente> findByCodiceAzienda(String codiceAzienda);

}
