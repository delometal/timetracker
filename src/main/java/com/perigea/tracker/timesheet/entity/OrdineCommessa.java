package com.perigea.tracker.timesheet.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.RelazioneIdOrdineCommessaKey;

@Entity
@IdClass(RelazioneIdOrdineCommessaKey.class)
@Table(name = "ordine_commessa")
public class OrdineCommessa extends BaseEntity{

	@Id
	@Column(name="codice_commessa")
	private String codiceCommessa;
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa", nullable = false)
	private CommessaFatturabile commessaFatturabile;

	@Id
	@Column(name = "numero_ordine_cliente")
	private String numeroOrdineCliente;

	@ManyToOne
	@JoinColumn(name = "ragione_sociale_cliente", referencedColumnName = "ragione_sociale_cliente")
	private AnagraficaCliente ragioneSocialeCliente;

	@Column(name = "data_ordine")
	private Date dataOrdine;

	@Column(name = "importo_ordine")
	private Double importoOrdine;

	@Column(name = "data_inizio")
	private Date dataInizio;

	@Column(name = "data_fine")
	private Date dataFine;

	@Column(name = "importo_residuo")
	private Double importoResiduo;


	public CommessaFatturabile getCommessaFatturabile() {
		return commessaFatturabile;
	}

	public void setCommessaFatturabile(CommessaFatturabile commessaFatturabile) {
		this.commessaFatturabile = commessaFatturabile;
	}

	public String getNumeroOrdineCliente() {
		return numeroOrdineCliente;
	}

	public void setNumeroOrdineCliente(String numeroOrdineCliente) {
		this.numeroOrdineCliente = numeroOrdineCliente;
	}

	public AnagraficaCliente getRagioneSocialeCliente() {
		return ragioneSocialeCliente;
	}

	public void setRagioneSocialeCliente(AnagraficaCliente ragioneSocialeCliente) {
		this.ragioneSocialeCliente = ragioneSocialeCliente;
	}

	public Date getDataOrdine() {
		return dataOrdine;
	}

	public void setDataOrdine(Date dataOrdine) {
		this.dataOrdine = dataOrdine;
	}

	public Double getImportoOrdine() {
		return importoOrdine;
	}

	public void setImportoOrdine(Double importoOrdine) {
		this.importoOrdine = importoOrdine;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public Double getImportoResiduo() {
		return importoResiduo;
	}

	public void setImportoResiduo(Double importoResiduo) {
		this.importoResiduo = importoResiduo;
	}

}