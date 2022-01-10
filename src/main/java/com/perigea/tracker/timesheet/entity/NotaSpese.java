package com.perigea.tracker.timesheet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.RelazioneIdNotaSpeseKey;
import com.perigea.tracker.timesheet.enumerator.TipoCostoNotaSpeseType;

@Entity
@IdClass(RelazioneIdNotaSpeseKey.class)
@Table(name = "nota_spese")
public class NotaSpese extends BaseEntity {

	@Id
	@Column(name = "anno_di_riferimento")
	private Integer annoDiRiferimento;

	@Id
	@Column(name = "mese_di_riferimento")
	private Integer meseDiRiferimento;

	@Id
	@Column(name="codice_persona")
	private String codicePersona;
	
	@MapsId
	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona")
	private Utente utenteSpesa;

	@Id
	@Column(name="codice_commessa")
	private String codiceCommessa;
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "codice_commessa", nullable = false, referencedColumnName = "codice_commessa")
	private Commessa commessaSpesa;

	@Id
	@Column(name = "giorno_di_riferimento")
	private Integer giornoDiRiferimento;

	@Id
	@Column(name = "tipo_costo")
	@Enumerated(EnumType.STRING)
	private TipoCostoNotaSpeseType tipoCostoNotaSpeseType;

	@Column(name = "importo")
	private Double importo;

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

	public Utente getUtenteSpesa() {
		return utenteSpesa;
	}

	public void setUtenteSpesa(Utente utenteSpesa) {
		this.utenteSpesa = utenteSpesa;
	}

	public Commessa getCommessaSpesa() {
		return commessaSpesa;
	}

	public void setCommessaSpesa(Commessa commessaSpesa) {
		this.commessaSpesa = commessaSpesa;
	}

	public Integer getGiornoDiRiferimento() {
		return giornoDiRiferimento;
	}

	public void setGiornoDiRiferimento(Integer giornoDiRiferimento) {
		this.giornoDiRiferimento = giornoDiRiferimento;
	}


	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
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

	public TipoCostoNotaSpeseType getTipoCostoNotaSpeseType() {
		return tipoCostoNotaSpeseType;
	}

	public void setTipoCostoNotaSpeseType(TipoCostoNotaSpeseType tipoCostoNotaSpeseType) {
		this.tipoCostoNotaSpeseType = tipoCostoNotaSpeseType;
	}
	
	


}