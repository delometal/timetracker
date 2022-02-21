package com.perigea.tracker.timesheet.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.perigea.tracker.commons.enums.RichiestaType;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Builder
@Table(name = "richiesta")
@EqualsAndHashCode(callSuper = true)
public class Richiesta extends BaseEntity {

	private static final long serialVersionUID = 1163648805713862098L;

	@Id
	@Column(name = "codice_richiesta")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codiceRichiesta;

	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	private RichiestaType tipo;
	
	@OneToOne
	@JoinColumn(name = "codice_persona")
	private Utente richiedente;
	
	@Builder.Default
	@OneToMany(mappedBy = "richiesta")
	private List<Timesheet> timesheetRequests = new ArrayList<>();

	@Builder.Default
	@OrderBy("timestamp DESC")
	@OneToMany(mappedBy = "richiesta", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RichiestaHistory> history = new ArrayList<>();
	
	public void addRichiestaHistory(RichiestaHistory richiestaHistory) {
		this.history.add(richiestaHistory);
	}
	
	public void removeRichiestaHistory(RichiestaHistory richiestaHistory) {
		this.history.remove(richiestaHistory);
	}
	
	public void addTimesheet(Timesheet timesheet) {
		this.timesheetRequests.add(timesheet);
	}
	
	public void removeTimesheet(Timesheet timesheet) {
		this.timesheetRequests.remove(timesheet);
	}
	
}