package com.perigea.tracker.timesheet.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "commessa_non_fatturabile")
@EqualsAndHashCode(callSuper = true)
public class CommessaNonFatturabile extends BaseEntity {

	private static final long serialVersionUID = 2256677862368637963L;

	@Id
	@Column(name = "codice_commessa", nullable = false)
	private String codiceCommessa;

	@PrimaryKeyJoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa")
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Commessa commessa;
	
	@Column(name = "descrizione")
	private String descrizione;

}