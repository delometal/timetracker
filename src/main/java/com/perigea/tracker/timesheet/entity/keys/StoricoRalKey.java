package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the ral_storico database table.
 * 
 */
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class StoricoRalKey implements Serializable {

	private static final long serialVersionUID = -4547955363488697017L;

	@Column(name="codice_persona")
	private String codicePersona;

	@Column(name="decorrenza_ral")
	private LocalDate decorrenzaRal;

	@Column(name="data_scadenza_ral")
	private LocalDate dataScadenzaRal;

}