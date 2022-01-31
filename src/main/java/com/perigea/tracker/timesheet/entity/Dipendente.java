package com.perigea.tracker.timesheet.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "dipendente")
@EqualsAndHashCode(callSuper = true)
public class Dipendente extends Anagrafica {

	private static final long serialVersionUID = -548714120900727181L;

	@Column(name = "data_assunzione")
	private LocalDate dataAssunzione;

	@Column(name = "data_cessazione")
	private LocalDate dataCessazione;
	
	@OneToOne(mappedBy = "dipendente", optional = true, cascade = CascadeType.ALL, orphanRemoval = true)
	private DatiEconomiciDipendente economics;
	
}