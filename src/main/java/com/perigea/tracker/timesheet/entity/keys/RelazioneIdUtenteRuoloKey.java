package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

import com.perigea.tracker.timesheet.enumerator.RuoloType;

public class RelazioneIdUtenteRuoloKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4772185743639881566L;

	@Column(name="codice_persona")
	private String codicePersona;
	
	@Column(name="ruolo")
	private RuoloType ruoloType;

	public String getCodicePersona() {
		return codicePersona;
	}

	public void setCodicePersona(String codicePersona) {
		this.codicePersona = codicePersona;
	}

	public RuoloType getRuoloType() {
		return ruoloType;
	}

	public void setRuoloType(RuoloType ruoloType) {
		this.ruoloType = ruoloType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codicePersona, ruoloType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelazioneIdUtenteRuoloKey other = (RelazioneIdUtenteRuoloKey) obj;
		return Objects.equals(codicePersona, other.codicePersona) && ruoloType == other.ruoloType;
	}
	
	
}
