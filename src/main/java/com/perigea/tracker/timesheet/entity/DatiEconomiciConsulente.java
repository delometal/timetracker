package com.perigea.tracker.timesheet.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "dati_economici_consulente")
@EqualsAndHashCode(callSuper = true)
public class DatiEconomiciConsulente extends BaseEntity {

	private static final long serialVersionUID = -3690536458436806691L;

    @Id
	@Column(name = "codice_persona")
	private String codicePersona;
	
    @Column(name = "data_ingaggio")
	private LocalDate datIngaggio;
    
    @Column(name = "decorrenza_assegnazione_centro_di_costo")
	private LocalDate decorrenzaAssegnazioneCentroDiCosto;
    
    @Column(name = "codice_centro_di_costo")
	private String codiceCentroDiCosto;
	
	@Column(name = "tipo_ingaggio")
	private Float tipoIngaggio;
	
	@Column(name = "costo_giornaliero")
	private Float costoGiornaliero;
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "codice_persona")
	private Consulente consulente;
		
}