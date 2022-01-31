package com.perigea.tracker.timesheet.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "consulente")
@EqualsAndHashCode(callSuper = true)
public class Consulente extends Anagrafica {

	private static final long serialVersionUID = -548714120900727181L;

	@Column(name = "partita_iva", unique = true)
	private String partitaIva;
	
	@Column(name = "costo")
	private BigDecimal costo;
	
	@Column(name = "data_assunzione")
	private LocalDate dataAssunzione;

	@Column(name = "data_cessazione")
	private LocalDate dataCessazione;
	
	@OneToOne(mappedBy = "consulente", optional = true)
	private DatiEconomiciConsulente economics;

}