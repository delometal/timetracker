package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

import com.perigea.tracker.timesheet.enumerator.TipoCostoNotaSpeseType;

public class RelazioneIdNotaSpeseKey implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1084967773079390935L;
	
	@Column(name="anno_di_riferimento")
	private Integer annoDiRiferimento;
	
	@Column(name="mese_di_riferimento")
	private Integer meseDiRiferimento;
	
	@Column(name="codice_persona")
	private String codicePersona;
	
	@Column(name="codice_commessa")
	private String codiceCommessa;
	
	@Column(name="giorno_di_riferimento")
	private Integer giornoDiRiferimento;
	
	@Column(name="tipo_costo")
	private TipoCostoNotaSpeseType tipoCostoNotaSpeseType;
	
	public Integer getAnnoDiRiferimento() {
		return annoDiRiferimento;
	}
	public void setAnnoDiRiferimento(Integer annoDiRiferimento) {
		this.annoDiRiferimento = annoDiRiferimento;
	}
	public Integer getMeseDiRiferimento() {
		return meseDiRiferimento;
	}
	public void setMeseDiRiferimento(Integer meseDiRiferimento) {
		this.meseDiRiferimento = meseDiRiferimento;
	}
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
	public Integer getGiornoDiRiferimento() {
		return giornoDiRiferimento;
	}
	public void setGiornoDiRiferimento(Integer giornoDiRiferimento) {
		this.giornoDiRiferimento = giornoDiRiferimento;
	}
	
	
	public TipoCostoNotaSpeseType getTipoCostoNotaSpeseType() {
		return tipoCostoNotaSpeseType;
	}
	public void setTipoCostoNotaSpeseType(TipoCostoNotaSpeseType tipoCostoNotaSpeseType) {
		this.tipoCostoNotaSpeseType = tipoCostoNotaSpeseType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		return Objects.hash(annoDiRiferimento, codiceCommessa, codicePersona, giornoDiRiferimento, meseDiRiferimento,
				tipoCostoNotaSpeseType);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelazioneIdNotaSpeseKey other = (RelazioneIdNotaSpeseKey) obj;
		return Objects.equals(annoDiRiferimento, other.annoDiRiferimento)
				&& Objects.equals(codiceCommessa, other.codiceCommessa)
				&& Objects.equals(codicePersona, other.codicePersona)
				&& Objects.equals(giornoDiRiferimento, other.giornoDiRiferimento)
				&& Objects.equals(meseDiRiferimento, other.meseDiRiferimento)
				&& Objects.equals(tipoCostoNotaSpeseType, other.tipoCostoNotaSpeseType);
	}
	
	
}
