package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

public class RelazioneIdTimeSheetKey implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3117858353342771303L;

	@Column(name = "anno_di_riferimento")
	private Integer annoDiRiferimento;
	
	@Column(name = "mese_di_riferimento")
	private Integer meseDiRiferimento;
	
	@Column(name = "giorno_di_riferimento")
	private Integer giornoDiRiferimento;
	
	
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
	
	public Integer getGiornoDiRiferimento() {
		return giornoDiRiferimento;
	}
	public void setGiornoDiRiferimento(Integer giornoDiRiferimento) {
		this.giornoDiRiferimento = giornoDiRiferimento;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(annoDiRiferimento, giornoDiRiferimento, meseDiRiferimento);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelazioneIdTimeSheetKey other = (RelazioneIdTimeSheetKey) obj;
		return Objects.equals(annoDiRiferimento, other.annoDiRiferimento)
				&& Objects.equals(giornoDiRiferimento, other.giornoDiRiferimento)
				&& Objects.equals(meseDiRiferimento, other.meseDiRiferimento);
	}
	
	

}
