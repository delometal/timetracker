package com.perigea.tracker.timesheet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * The persistent class for centro_di_costo database table.
 * 
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name="centro_di_costo")
public class CentroDiCosto extends BaseEntity {

	private static final long serialVersionUID = 144709517490872712L;

	@Id
	@Column(name="codice_centro_di_costo")
	private String codiceCentroDiCosto;
	
	@Column(name = "descrizione")
	private String descrizione;

}