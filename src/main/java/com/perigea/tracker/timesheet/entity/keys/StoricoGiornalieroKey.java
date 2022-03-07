package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the costo_giornaliero_storico database table.
 * 
 */
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class StoricoGiornalieroKey implements Serializable {

	private static final long serialVersionUID = 2431629129940857170L;

	@Column(name="codice_persona")
	private String codicePersona;

	@Column(name="decorrenza_costo")
	private LocalDate decorrenzaCosto;

	@Column(name="data_scadenza_costo")
	private LocalDate dataScadenzaCosto;

}