package com.perigea.tracker.timesheet.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.perigea.tracker.commons.enums.ApprovalStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "richiesta_history")
@EqualsAndHashCode(callSuper = true)
public class RichiestaHistory extends BaseEntity {

	private static final long serialVersionUID = 1163648805713862098L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codiceRichiestaHistory;

	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	@Column(name = "stato")
	@Enumerated(EnumType.STRING)
	private ApprovalStatus stato;
	
	@OneToOne
	@JoinColumn(name = "codice_persona")
	private Utente responsabile;

	@ManyToOne
	@JoinColumn(name = "codice_richiesta")
	private Richiesta richiesta;
	
}