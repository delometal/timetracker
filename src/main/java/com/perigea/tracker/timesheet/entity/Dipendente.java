package com.perigea.tracker.timesheet.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
@DiscriminatorValue("DIPENDENTE")
@EqualsAndHashCode(callSuper = true)
public class Dipendente extends Personale {

	private static final long serialVersionUID = -548714120900727181L;

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(mappedBy = "personale", optional = true, cascade = CascadeType.ALL, orphanRemoval = true)
	private DatiEconomiciDipendente economics;

	@OneToMany(mappedBy = "personale")
	private List<StoricoKmRimborsabiliPerGiorno> storicoKmRimborsabiliPerGiorno = new ArrayList<>();
	
	@OneToMany(mappedBy = "personale")
	private List<StoricoLivelloContrattuale> storicoLivelloContrattuale = new ArrayList<>();

	@OneToMany(mappedBy = "personale")
	private List<StoricoPremio> storicoPremio = new ArrayList<>();

	@OneToMany(mappedBy = "personale")
	private List<StoricoRal> storicoRal = new ArrayList<>();

	@OneToMany(mappedBy = "personale")
	private List<StoricoRimborsiKm> storicoRimborsiKm = new ArrayList<>();

	@OneToMany(mappedBy = "personale")
	private List<StoricoCostoGiornaliero> storicoCostoGiornaliero = new ArrayList<>();
}