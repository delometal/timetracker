package com.perigea.tracker.timesheet.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

import com.perigea.tracker.timesheet.enums.CommessaFatturabileType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@DiscriminatorValue("F")
@EqualsAndHashCode(callSuper = true)
public class CommessaFatturabile extends Commessa {

	private static final long serialVersionUID = 1347579222327736820L;

	@Column(name = "descrizione_commessa_cliente")
	private String descrizioneCommessaCliente;

	@Column(name = "data_inizio_commessa")
	private Date dataInizioCommessa;

	@Column(name = "tipo_commessa_fatturabile")
	@Enumerated(EnumType.STRING)
	private CommessaFatturabileType tipoCommessaFatturabileType;

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

}