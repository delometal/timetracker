package com.perigea.tracker.timesheet.entity;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.perigea.tracker.commons.enums.CommessaType;
import com.perigea.tracker.timesheet.entity.keys.TimesheetEntryKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "time_sheet_entry")
@EqualsAndHashCode(callSuper = true)
public class TimesheetEntry extends BaseEntity {

	private static final long serialVersionUID = -3241359472237290256L;

	@EmbeddedId
	private TimesheetEntryKey id;

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", insertable = false, updatable = false),
			@JoinColumn(name = "anno_di_riferimento", referencedColumnName = "anno_di_riferimento", insertable = false, updatable = false),
			@JoinColumn(name = "mese_di_riferimento", referencedColumnName = "mese_di_riferimento", insertable = false, updatable = false) })
	private Timesheet timesheet;

	@ManyToOne
	@JoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa", nullable = false, updatable = false, insertable = false)
	private Commessa commessa;
	
	@OneToMany (mappedBy = "timesheetEntry", cascade = CascadeType.ALL)
	private List<NotaSpese> noteSpesa;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_commessa")
	private CommessaType tipoCommessa;

	@Column(name = "ore")
	private Integer ore;

	@Column(name = "trasferta")
	private Boolean trasferta;
	
	@Column(name = "staordinario")
	private Boolean straordinario;	
	
	
	public void addNotaSpese(NotaSpese notaSpesa) {
		this.noteSpesa.add(notaSpesa);
		notaSpesa.setTimesheetEntry(this);
	}

	public void removeNotaSpese(NotaSpese notaSpesa) {
		this.noteSpesa.remove(notaSpesa);
		notaSpesa.setTimesheetEntry(null);
	}

}