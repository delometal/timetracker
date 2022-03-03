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

import com.perigea.tracker.commons.enums.IngaggioType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "dati_economici_consulente")
@EqualsAndHashCode(callSuper = true)
public class DatiEconomiciConsulente extends BaseEntity{

	private static final long serialVersionUID = -3690536458436806691L;
   	
	@Id
	@Column(name = "codice_persona", insertable = false, updatable = false)
	private String codicePersona;
    
    @Column(name = "codice_centro_di_costo", insertable = false, updatable = false)
	private String codiceCentroDiCosto;
	
    // centroDiCosto e decorrenza generano storicoAssegnazioneCentroDiCosto
	@ManyToOne
	@JoinColumn(name = "codice_centro_di_costo")
	private CentroDiCosto centroDiCosto;
    
    @Column(name = "decorrenza_assegnazione_centro_di_costo")
	private LocalDate decorrenzaAssegnazioneCentroDiCosto;

	
    @Column(name = "data_ingaggio")
	private LocalDate dataIngaggio;

    
    // TODO ??? (dovrebbe essere IngaggioType?
    // 			 Storico?)
    @Enumerated(EnumType.STRING)
	@Column(name = "tipo_ingaggio")
	private IngaggioType tipoIngaggio;
	
	// costoGiornaliero e dataDecorrenza generano storicoCostoGiornaliero
	@Column(name = "costo_giornaliero")
	private Float costoGiornaliero;
	
	@Column(name = "data_decorrenza_costo")
	private LocalDate dataDecorrenzaCosto;
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "codice_persona")
	private Consulente personale;
  
	
}