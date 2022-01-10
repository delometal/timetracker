package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

public class RelazioneIdRelazioneDipendenteCommessaKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2275333400404753544L;

	@Column(name="codice_persona")
	private String codicePersona;
	
	@Column(name="codice_commessa")
	private String codiceCommessa;
	public String getCodicePersona() {
		return codicePersona;
	}
	public void setCodicePersona(String codicePersona) {
		this.codicePersona = codicePersona;
	}
	public String getCodiceCommessa() {
		return codiceCommessa;
	}
	public void setCodiceCommessa(String codiceCommessa) {
		this.codiceCommessa = codiceCommessa;
	}
	@Override
	public int hashCode() {
		return Objects.hash(codiceCommessa, codicePersona);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelazioneIdRelazioneDipendenteCommessaKey other = (RelazioneIdRelazioneDipendenteCommessaKey) obj;
		return Objects.equals(codiceCommessa, other.codiceCommessa)
				&& Objects.equals(codicePersona, other.codicePersona);
	}
	
	
	
	
	
}
