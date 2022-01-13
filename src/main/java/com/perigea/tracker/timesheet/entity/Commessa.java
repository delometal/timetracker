package com.perigea.tracker.timesheet.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.enumerator.TipoCommessaType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "commessa")
@Data
@EqualsAndHashCode(callSuper = true)
public class Commessa extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7155033574935911917L;

	@Id
	@Column(name = "codice_commessa", nullable = false)
	private String codiceCommessa;

	@Column(name = "tipo_commessa")
	@Enumerated(EnumType.STRING)
	private TipoCommessaType commessaType;

	@OneToOne(mappedBy = "commessaSpesa")
	private NotaSpese notaSpese;

	@OneToMany(mappedBy = "commessaTimeSheet")
	private List<TimeSheet> timeSheet = new ArrayList<>();

	@OneToOne(mappedBy = "commessaNonFatturabile", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private CommessaNonFatturabile commessaNonFatturabile;

	@OneToMany(mappedBy = "commessa")
	private List<RelazioneDipendenteCommessa> relazione = new ArrayList<>();

	@OneToOne(mappedBy = "commessaFatturabile")
	private CommessaFatturabile commessaFatturabile;
	
	public void addCommessa(TimeSheet commessa) {
		this.timeSheet.add(commessa);
		commessa.setCommessaTimeSheet(this);
	}

	public void removeCommessa(TimeSheet commessa) {
		this.timeSheet.remove(commessa);
		commessa.setCommessaTimeSheet(null);
	}
}