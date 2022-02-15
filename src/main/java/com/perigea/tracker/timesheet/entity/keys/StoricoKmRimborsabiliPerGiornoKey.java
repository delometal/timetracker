package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the km_rimborsabili_per_giorno_storico database table.
 * 
 */
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class StoricoKmRimborsabiliPerGiornoKey implements Serializable {

	private static final long serialVersionUID = -665834465185811209L;

	@Column(name="codice_persona", nullable = false)
	private String codicePersona;

	@Column(name="decorrenza_km_rimborsabili")
	private LocalDate decorrenzaKmRimborsabili;

	@Column(name="data_scadenza_km_rimborsabili")
	private LocalDate dataScadenzaKmRimborsabili;

}