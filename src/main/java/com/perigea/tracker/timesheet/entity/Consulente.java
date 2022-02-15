package com.perigea.tracker.timesheet.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@DiscriminatorValue("CONSULENTE")
@EqualsAndHashCode(callSuper = true)
public class Consulente extends Personale {

	private static final long serialVersionUID = -548714120900727181L;

	@Column(name = "partita_iva", unique = true)
	private String partitaIva;
	
	@Column(name = "costo")
	private BigDecimal costo;
		
	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(mappedBy = "personale", optional = true, cascade = CascadeType.ALL, orphanRemoval = true)
	private DatiEconomiciConsulente economics;
	
	@OneToMany(mappedBy = "personale")
	private List<StoricoCostoGiornaliero> storicoCostoGiornaliero = new ArrayList<>();
	
}