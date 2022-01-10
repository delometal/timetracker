package com.perigea.tracker.timesheet.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.RelazioneIdRelazioneDipendenteCommessaKey;

@Entity
@IdClass(RelazioneIdRelazioneDipendenteCommessaKey.class)
@Table(name = "relazione_dipendente_commessa")
public class RelazioneDipendenteCommessa extends BaseEntity{

	@Id
	@Column(name="codice_persona")
	private String codicePersona;
	
	@MapsId
	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", nullable = false)
	private Utente utente;

	@Id
	@Column(name="codice_commessa")
	private String codiceCommessa;
	
	@MapsId
	@ManyToOne
	@JoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa", nullable = false)
	private Commessa commessa;

	@Column(name = "data_inizio_allocazione")
	private Date dataInizioAllocazione;

	@Column(name = "data_fine_allocazione")
	private Date dataFineAllocazione;

	@Column(name = "tariffa")
	private Double tariffa;

	@Column(name = "giorni_previsti")
	private Integer giorniPrevisti;

	@Column(name = "giorni_erogati")
	private Integer giorniErogati;

	@Column(name = "giorni_residui")
	private Integer giorniResidui;

	@Column(name = "importo_previsto")
	private Double importoPrevisto;

	@Column(name = "importo_erogato")
	private Double importoErogato;
	
	@Column(name="importo_residuo")
	private Double importoResiduo;
	

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Commessa getCommessa() {
		return commessa;
	}

	public void setCommessa(Commessa commessa) {
		this.commessa = commessa;
	}

	public Date getDataInizioAllocazione() {
		return dataInizioAllocazione;
	}

	public void setDataInizioAllocazione(Date dataInizioAllocazione) {
		this.dataInizioAllocazione = dataInizioAllocazione;
	}

	public Date getDataFineAllocazione() {
		return dataFineAllocazione;
	}

	public void setDataFineAllocazione(Date dataFineAllocazione) {
		this.dataFineAllocazione = dataFineAllocazione;
	}

	public Double getTariffa() {
		return tariffa;
	}

	public void setTariffa(Double tariffa) {
		this.tariffa = tariffa;
	}

	public Integer getGiorniPrevisti() {
		return giorniPrevisti;
	}

	public void setGiorniPrevisti(Integer giorniPrevisti) {
		this.giorniPrevisti = giorniPrevisti;
	}

	public Integer getGiorniErogati() {
		return giorniErogati;
	}

	public void setGiorniErogati(Integer giorniErogati) {
		this.giorniErogati = giorniErogati;
	}

	public Integer getGiorniResidui() {
		return giorniResidui;
	}

	public void setGiorniResidui(Integer giorniResidui) {
		this.giorniResidui = giorniResidui;
	}

	public Double getImportoPrevisto() {
		return importoPrevisto;
	}

	public void setImportoPrevisto(Double importoPrevisto) {
		this.importoPrevisto = importoPrevisto;
	}

	public Double getImportoErogato() {
		return importoErogato;
	}

	public void setImportoErogato(Double importoErogato) {
		this.importoErogato = importoErogato;
	}

	public Double getImportoResiduo() {
		return importoResiduo;
	}

	public void setImportoResiduo(Double importoResiduo) {
		this.importoResiduo = importoResiduo;
	}

	
}