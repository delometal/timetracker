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
public class StoricoContrattoTypeKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5173086455798037732L;
	@Column(name="codice_persona")
	private String codicePersona;

	@Column(name="decorrenza")
	private LocalDate decorrenza;
	
	@Column(name="scadenza")
	private LocalDate scadenza;

}
