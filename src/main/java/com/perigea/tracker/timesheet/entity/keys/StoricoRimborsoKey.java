package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class StoricoRimborsoKey implements Serializable{
	
	private static final long serialVersionUID = 7669835962239582371L;

	@Column(name="codice_persona")
	private String codicePersona;

	@Column(name="decorrenza_rimborso")
	private LocalDate decorrenzaRimborso;

	@Column(name="data_scadenza_rimborso")
	private LocalDate dataScadenzaRimborso;
}
