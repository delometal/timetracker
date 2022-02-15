package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the livello_contrattuale_storico database table.
 * 
 */
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class StoricoLivelloContrattualeKey implements Serializable {

	private static final long serialVersionUID = -482627216164497325L;

	@Column(name="codice_persona")
	private String codicePersona;

	@Column(name="decorrenza_livello")
	private LocalDate decorrenzaLivello;

	@Column(name="data_scadenza_livello")
	private LocalDate dataScadenzaLivello;

}