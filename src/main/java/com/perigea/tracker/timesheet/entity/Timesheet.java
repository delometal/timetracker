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
import com.perigea.tracker.timesheet.entity.keys.TimesheetMensileKey;
import com.perigea.tracker.timesheet.enums.StatoType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "time_sheet")
public class Timesheet extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3463686493881931397L;

	@EmbeddedId
	private TimesheetMensileKey id;

	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", insertable = false, updatable = false)
	private Utente utenteTimesheet;

	@Column(name = "ore_totali")
	private Integer oreTotali;

	@Column(name = "stato_time_sheet")
	@Enumerated(EnumType.STRING)
	private StatoType statoType;

	@OneToMany(mappedBy = "timeSheet", cascade = CascadeType.ALL)
	private List<TimesheetData> data = new ArrayList<TimesheetData>();

	public void addTimesheet(TimesheetData timesheet) {
		this.data.add(timesheet);
		timesheet.setTimeSheet(this);
	}

	public void removeTimesheet(TimesheetData timesheet) {
		this.data.remove(timesheet);
		timesheet.setTimeSheet(null);

	}
}
