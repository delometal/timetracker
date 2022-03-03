package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the assegnazione_centro_costo_storico database table.
 * 
 */
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class StoricoAssegnazioneCentroCostoKey implements Serializable {

	private static final long serialVersionUID = -6030996923171355418L;

	@Column(name="codice_persona")
	private String codicePersona;

	@Column(name="decorrenza_assegnazione_centro_di_costo")
	private LocalDate decorrenzaAssegnazioneCentroDiCosto;

	@Column(name="data_scadenza_assegnazione_centro_costo")
	private LocalDate dataScadenzaAssegnazioneCentroCosto;

}