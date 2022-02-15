package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the rimborsi_km_storico database table.
 * 
 */
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class StoricoRimborsiKmKey implements Serializable {

	private static final long serialVersionUID = -821095113410092931L;

	@Column(name="codice_persona")
	private String codicePersona;

	@Column(name="decorrenza_rimborsi_km")
	private LocalDate decorrenzaRimborsiKm;

	@Column(name="data_scadenza_rimborsi_km")
	private LocalDate dataScadenzaRimborsiKm;

}