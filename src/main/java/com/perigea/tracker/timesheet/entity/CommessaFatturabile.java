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

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "commessa_fatturabile")
@Data
@EqualsAndHashCode(callSuper = true)
public class CommessaFatturabile extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1347579222327736820L;

	@Id
	@Column(name = "codice_commessa", nullable = false)
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

}