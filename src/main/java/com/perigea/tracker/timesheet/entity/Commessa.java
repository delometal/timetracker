package com.perigea.tracker.timesheet.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.enumerator.CommessaType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "commessa")
@EqualsAndHashCode(callSuper = true)
public class Commessa extends BaseEntity {

	private static final long serialVersionUID = 7155033574935911917L;

	@Id
	@Column(name = "codice_commessa", nullable = false)
	private String codiceCommessa;

	@Column(name = "tipo_commessa")
	@Enumerated(EnumType.STRING)
	private CommessaType commessaType;
	
	@OneToOne(mappedBy = "commessaSpesa")
	private NotaSpese notaSpese;

	@OneToMany(mappedBy = "commessaTimesheet")
	private List<Timesheet> timesheet = new ArrayList<>();

	@OneToMany(mappedBy = "commessa")
	private List<DipendenteCommessa> dipendenteCommessa = new ArrayList<>();

}