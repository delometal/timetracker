package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.perigea.tracker.timesheet.enumerator.RuoloType;

import lombok.Data;

@Data
@Embeddable
public class RelazioneIdUtenteRuoloKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4772185743639881566L;

	@Column(name = "codice_persona")
	private String codicePersona;

	@Column(name = "ruolo")
	private RuoloType ruoloType;

}
