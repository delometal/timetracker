package com.perigea.tracker.timesheet.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.enums.CommessaType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "commessa")
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo_commessa", discriminatorType = DiscriminatorType.STRING)
public class Commessa extends BaseEntity {

	private static final long serialVersionUID = 7155033574935911917L;

	@Id
	@Column(name = "codice_commessa", nullable = false)
	private String codiceCommessa;
	
	@Column(name = "descrizione_commessa")
	private String descrizioneCommessa;

	@Column(name = "tipo_commessa", nullable = false, insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private CommessaType tipoCommessa;
	
	@OneToOne(mappedBy = "commessa")
	private NotaSpese notaSpese;

	@OneToMany(mappedBy = "commessa")
	private List<TimesheetEntry> timesheet = new ArrayList<>();

	@OneToMany(mappedBy = "id.commessa")
	private List<DipendenteCommessa> dipendente = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "partita_iva", referencedColumnName = "partita_iva", nullable = false)
	private AnagraficaCliente cliente;

}