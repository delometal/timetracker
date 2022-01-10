package com.perigea.tracker.timesheet.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.enumerator.TipoCommessaFatturabileType;

@Entity
@Table(name = "commessa_fatturabile")
public class CommessaFatturabile extends BaseEntity {

	@Id
	@Column(name="codice_commessa",nullable=false)
	private String codiceCommessa;
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa", nullable = false)
	private Commessa commessaFatturabile;

	@ManyToOne
	@JoinColumn(name = "ragione_sociale_cliente", referencedColumnName = "ragione_sociale_cliente", nullable = false)
	private AnagraficaCliente ragioneSociale;

	@Column(name = "descrizione_commessa_perigea")
	private String descrizioneCommessaPerigea;

	@Column(name = "descrizione_commessa_cliente")
	private String descrizioneCommessaCliente;

	@Column(name = "data_inizio_commessa")
	private Date dataInizioCommessa;
	
	@Column(name = "tipo_commessa")
	@Enumerated(EnumType.STRING)
	private TipoCommessaFatturabileType tipoCommessaFatturabileType;

	@Column(name = "data_fine_commessa")
	private Date dataFineCommessa;

	@Column(name = "importo_commessa_iniziale_presunto")
	private Double importoCommessaInizialePresunto;

	@Column(name = "totale_estensioni")
	private Double totaleEstensioni;

	@Column(name = "ordine_interno_corrente")
	private Double ordineInternoCorrente;

	@Column(name = "totale_ordine_cliente_formale")
	private Double totaleOrdineClienteFormale;

	@Column(name = "totale_ordine")
	private Double totaleOrdine;

	@Column(name = "totale_ricavi_da_inizio_commessa")
	private Double totaleRicaviDaInizioCommessa;

	@Column(name = "totale_ricavi_da_inizio_anno")
	private Double totaleRicaviDaInizioAnno;

	@Column(name = "totale_costi_da_inizio_commessa")
	private Double totaleCostiDaInizioCommessa;

	@Column(name = "totale_costi_da_inizio_anno")
	private Double totaleCostiDaInizioAnno;

	@Column(name = "totale_fatturato_da_inizio_commessa")
	private Double totaleFatturatoreDaInizioCommessa;

	@Column(name = "totale_fatturato_da_inizio_anno")
	private Double totaleFatturatoDaInizioAnno;

	@Column(name = "margine_iniziale")
	private Double margineIniziale;

	@Column(name = "margine_da_inizio_commessa")
	private Double margineDaInizioCommessa;

	@Column(name = "margine_da_inizio_anno")
	private Double margineDaInizioAnno;

	@Column(name = "percentuale_avanzamento_costi")
	private Double percentualeAvanzamentoCosti;

	@Column(name = "percentuale_avanzamento_fatturazione")
	private Double percentualeAvanzamentoFatturazione;

	@Column(name = "percentuale_sconto")
	private Double percentualeSconto;

	@Column(name = "responsabile_commerciale")
	private String responsabileCommerciale;

	@OneToOne(mappedBy = "commessaFatturabile")
	private OrdineCommessa ordineCommessa;

	public Commessa getCommessaFat() {
		return commessaFatturabile;
	}
	
	

	public String getCodiceCommessa() {
		return codiceCommessa;
	}



	public void setCodiceCommessa(String codiceCommessa) {
		this.codiceCommessa = codiceCommessa;
	}



	public Commessa getCommessaFatturabile() {
		return commessaFatturabile;
	}



	public void setCommessaFatturabile(Commessa commessaFatturabile) {
		this.commessaFatturabile = commessaFatturabile;
	}



	public TipoCommessaFatturabileType getTipoCommessaFatturabileType() {
		return tipoCommessaFatturabileType;
	}



	public void setTipoCommessaFatturabileType(TipoCommessaFatturabileType tipoCommessaFatturabileType) {
		this.tipoCommessaFatturabileType = tipoCommessaFatturabileType;
	}



	public void setCommessaFat(Commessa commessaFat) {
		this.commessaFatturabile = commessaFat;
	}

	public AnagraficaCliente getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(AnagraficaCliente ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getDescrizioneCommessaPerigea() {
		return descrizioneCommessaPerigea;
	}

	public void setDescrizioneCommessaPerigea(String descrizioneCommessaPerigea) {
		this.descrizioneCommessaPerigea = descrizioneCommessaPerigea;
	}

	public String getDescrizioneCommessaCliente() {
		return descrizioneCommessaCliente;
	}

	public void setDescrizioneCommessaCliente(String descrizioneCommessaCliente) {
		this.descrizioneCommessaCliente = descrizioneCommessaCliente;
	}

	public Date getDataInizioCommessa() {
		return dataInizioCommessa;
	}

	public void setDataInizioCommessa(Date dataInizioCommessa) {
		this.dataInizioCommessa = dataInizioCommessa;
	}

	public Date getDataFineCommessa() {
		return dataFineCommessa;
	}

	public void setDataFineCommessa(Date dataFineCommessa) {
		this.dataFineCommessa = dataFineCommessa;
	}

	public Double getImportoCommessaInizialePresunto() {
		return importoCommessaInizialePresunto;
	}

	public void setImportoCommessaInizialePresunto(Double importoCommessaInizialePresunto) {
		this.importoCommessaInizialePresunto = importoCommessaInizialePresunto;
	}

	public Double getTotaleEstensioni() {
		return totaleEstensioni;
	}

	public void setTotaleEstensioni(Double totaleEstensioni) {
		this.totaleEstensioni = totaleEstensioni;
	}

	public Double getOrdineInternoCorrente() {
		return ordineInternoCorrente;
	}

	public void setOrdineInternoCorrente(Double ordineInternoCorrente) {
		this.ordineInternoCorrente = ordineInternoCorrente;
	}

	public Double getTotaleOrdineClienteFormale() {
		return totaleOrdineClienteFormale;
	}

	public void setTotaleOrdineClienteFormale(Double totaleOrdineClienteFormale) {
		this.totaleOrdineClienteFormale = totaleOrdineClienteFormale;
	}

	public Double getTotaleOrdine() {
		return totaleOrdine;
	}

	public void setTotaleOrdine(Double totaleOrdine) {
		this.totaleOrdine = totaleOrdine;
	}

	public Double getTotaleRicaviDaInizioCommessa() {
		return totaleRicaviDaInizioCommessa;
	}

	public void setTotaleRicaviDaInizioCommessa(Double totaleRicaviDaInizioCommessa) {
		this.totaleRicaviDaInizioCommessa = totaleRicaviDaInizioCommessa;
	}

	public Double getTotaleRicaviDaInizioAnno() {
		return totaleRicaviDaInizioAnno;
	}

	public void setTotaleRicaviDaInizioAnno(Double totaleRicaviDaInizioAnno) {
		this.totaleRicaviDaInizioAnno = totaleRicaviDaInizioAnno;
	}

	public Double getTotaleCostiDaInizioCommessa() {
		return totaleCostiDaInizioCommessa;
	}

	public void setTotaleCostiDaInizioCommessa(Double totaleCostiDaInizioCommessa) {
		this.totaleCostiDaInizioCommessa = totaleCostiDaInizioCommessa;
	}

	public Double getTotaleCostiDaInizioAnno() {
		return totaleCostiDaInizioAnno;
	}

	public void setTotaleCostiDaInizioAnno(Double totaleCostiDaInizioAnno) {
		this.totaleCostiDaInizioAnno = totaleCostiDaInizioAnno;
	}

	public Double getTotaleFatturatoreDaInizioCommessa() {
		return totaleFatturatoreDaInizioCommessa;
	}

	public void setTotaleFatturatoreDaInizioCommessa(Double totaleFatturatoreDaInizioCommessa) {
		this.totaleFatturatoreDaInizioCommessa = totaleFatturatoreDaInizioCommessa;
	}

	public Double getTotaleFatturatoDaInizioAnno() {
		return totaleFatturatoDaInizioAnno;
	}

	public void setTotaleFatturatoDaInizioAnno(Double totaleFatturatoDaInizioAnno) {
		this.totaleFatturatoDaInizioAnno = totaleFatturatoDaInizioAnno;
	}

	public Double getMargineIniziale() {
		return margineIniziale;
	}

	public void setMargineIniziale(Double margineIniziale) {
		this.margineIniziale = margineIniziale;
	}

	public Double getMargineDaInizioCommessa() {
		return margineDaInizioCommessa;
	}

	public void setMargineDaInizioCommessa(Double margineDaInizioCommessa) {
		this.margineDaInizioCommessa = margineDaInizioCommessa;
	}

	public Double getMargineDaInizioAnno() {
		return margineDaInizioAnno;
	}

	public void setMargineDaInizioAnno(Double margineDaInizioAnno) {
		this.margineDaInizioAnno = margineDaInizioAnno;
	}

	public Double getPercentualeAvanzamentoCosti() {
		return percentualeAvanzamentoCosti;
	}

	public void setPercentualeAvanzamentoCosti(Double percentualeAvanzamentoCosti) {
		this.percentualeAvanzamentoCosti = percentualeAvanzamentoCosti;
	}

	public Double getPercentualeAvanzamentoFatturazione() {
		return percentualeAvanzamentoFatturazione;
	}

	public void setPercentualeAvanzamentoFatturazione(Double percentualeAvanzamentoFatturazione) {
		this.percentualeAvanzamentoFatturazione = percentualeAvanzamentoFatturazione;
	}

	public Double getPercentualeSconto() {
		return percentualeSconto;
	}

	public void setPercentualeSconto(Double percentualeSconto) {
		this.percentualeSconto = percentualeSconto;
	}

	public String getResponsabileCommerciale() {
		return responsabileCommerciale;
	}

	public void setResponsabileCommerciale(String responsabileCommerciale) {
		this.responsabileCommerciale = responsabileCommerciale;
	}

	public OrdineCommessa getOrdineCommessa() {
		return ordineCommessa;
	}

	public void setOrdineCommessa(OrdineCommessa ordineCommessa) {
		this.ordineCommessa = ordineCommessa;
	}

}