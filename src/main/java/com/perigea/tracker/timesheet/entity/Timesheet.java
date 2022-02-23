package com.perigea.tracker.timesheet.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.perigea.tracker.commons.enums.ApprovalStatus;
import com.perigea.tracker.timesheet.entity.keys.TimesheetMensileKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "timesheet")
@EqualsAndHashCode(callSuper = true)
public class Timesheet extends BaseEntity {

	private static final long serialVersionUID = 3463686493881931397L;

	@EmbeddedId
	private TimesheetMensileKey id;

	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", insertable = false, updatable = false)
	private Personale personale;

	@Column(name = "ore_totali")
	private Integer oreTotali;

	@Column(name = "stato_time_sheet")
	@Enumerated(EnumType.STRING)
	private ApprovalStatus statoRichiesta;
	
	@Column(name = "codice_richiesta", insertable = false, updatable = false, unique = true)
	private Long codiceRichiesta;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.MERGE})
	@JoinColumn(name = "codice_richiesta", referencedColumnName = "codice_richiesta", insertable = true, updatable = true, nullable = false)
	private Richiesta richiesta;

	@OneToMany(mappedBy = "timesheet", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TimesheetEntry> entries = new ArrayList<TimesheetEntry>();

	public void addTimesheet(TimesheetEntry timesheet) {
		this.entries.add(timesheet);
		timesheet.setTimesheet(this);
	}

	public void removeTimesheet(TimesheetEntry timesheet) {
		this.entries.remove(timesheet);
		timesheet.setTimesheet(null);
	}
}
