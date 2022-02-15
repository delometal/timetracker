package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the premio_storico database table.
 * 
 */
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class StoricoPremioKey implements Serializable {

	private static final long serialVersionUID = 4466329828856839036L;

	@Column(name="codice_persona")
	private String codicePersona;

	@Column(name="data_assegnazione_premio")
	private LocalDate dataAssegnazionePremio;

}