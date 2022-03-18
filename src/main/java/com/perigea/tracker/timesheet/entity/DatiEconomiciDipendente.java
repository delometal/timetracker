package com.perigea.tracker.timesheet.entity;

import java.time.LocalDate;

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

import com.perigea.tracker.commons.enums.ContrattoType;
import com.perigea.tracker.commons.enums.JobTitle;
import com.perigea.tracker.commons.enums.LivelloContrattoType;
import com.perigea.tracker.commons.enums.SceltaTredicesimaType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "dati_economici_dipendente")
@EqualsAndHashCode(callSuper = true)
public class DatiEconomiciDipendente extends BaseEntity{

	private static final long serialVersionUID = -3690536458436806691L;
	
	@Id
	@Column(name = "codice_persona", insertable = false, updatable = false)
	private String codicePersona;

    // DecorrenzaAssegnazioneCentroDiCosto, CodiceCentroDiCosto -> StoricoCentroDiCosto
	@Column(name = "decorrenza_assegnazione_centro_di_costo", nullable = false)
	private LocalDate decorrenzaAssegnazioneCentroDiCosto;
    
    @Column(name = "codice_centro_di_costo", insertable = false, updatable = false)
	private String codiceCentroDiCosto;
	
    // DecorrenzaLivello, LivelloAttuale -> StoricoLivello
	@Column(name = "decorrenza_livello", nullable = false)
	private LocalDate decorrenzaLivello;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "livello_iniziale")
	private LivelloContrattoType livelloIniziale;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "livello_attuale")
	private LivelloContrattoType livelloAttuale;
	
	// DecorrenzaContratto, TipoContrattoAttuale -> StoricoContrattoType
	@Column(name = "decorrenza_tipo_contratto", nullable = false)
	private LocalDate decorrenzaTipoContratto;	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_contratto_iniziale")
	private ContrattoType tipoContrattoIniziale;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_contratto_attuale")
	private ContrattoType tipoContrattoAttuale;

	// RalAttuale, decorrenzaRal -> StoricoRal
	@Column(name = "decorrenza_ral_attuale", nullable = false)
	private LocalDate decorrenzaRalAttuale;
	
	@Column(name = "ral_iniziale")
	private Float ralIniziale;
	
	@Column(name = "ral_attuale")
	private Float ralAttuale;
	
	// RimborsoGiornaliero, decorrenzaRimborso -> StoricoRimborso
	@Column(name = "rimborso_giornaliero")
	private Float rimborsoGiornaliero;
	
	@Column(name = "decorrenza_rimborso", nullable = false)
	private LocalDate decorrenzaRimborso;
	
	// CostoGiornaliero, dataDecorrenzaCostoGiornaliero -> StoricoCostoGiornaliero
	@Column(name = "costo_giornaliero")
	private Float costoGiornaliero;
	
	@Column(name = "data_decorrenza_costo", nullable = false)
	private LocalDate dataDecorrenzaCosto;
	
	// UltimoPremio, dataUltimoPremio -> StoricoPremio
	@Column(name = "ultimo_premio")
	private Float ultimoPremio;
	
	@Column(name = "data_ultimo_premio", nullable = false)
	private LocalDate dataUltimoPremio;
	
	// ModelloAuto, RimborsoPerKm, DecorrenzaRimborsiKm -> StoricoRimborsiKm
	@Column(name = "modello_auto")
	private String modelloAuto;
	
	@Column(name = "rimborso_per_km")
	private Float rimborsoPerKm;
	
	@Column(name = "decorrenza_rimborsi_km", nullable = false)
	private LocalDate decorrenzaRimborsiKm;
	
	@Column(name = "kmPerGiorno")
	private Float kmPerGiorno;
	
	@Column(name = "decorrenza_km_rimborsabili", nullable = false)
	private LocalDate decorrenzaKmRimborsabili;
	
	@Column(name = "data_assegnazione_ticket")
	private LocalDate dataAssegnazioneTicket;

	@Enumerated(EnumType.STRING)
	@Column(name = "scelta_tredicesima")
	private SceltaTredicesimaType sceltaTredicesima;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "job_title")
	private JobTitle jobTitle;
	
	@Column(name = "archived")
	private Boolean archived;
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "codice_persona")
	private Dipendente personale;
	
	@ManyToOne
	@JoinColumn(name = "codice_centro_di_costo")
	private CentroDiCosto centroDiCosto;
		
}