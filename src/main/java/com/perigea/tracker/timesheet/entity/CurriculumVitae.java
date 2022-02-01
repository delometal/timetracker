package com.perigea.tracker.timesheet.entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "curriculum")
@EqualsAndHashCode(callSuper = true)
public class CurriculumVitae extends BaseEntity {

	private static final long serialVersionUID = 2349709548344649878L;

	@Id
	@Column(name = "codice_persona", nullable = false)
	private String codicePersona;
	
	@Lob
    @Column(name = "cv")
	@Type(type="org.hibernate.type.BinaryType")
    private byte[] cv;
	
	@Column(name = "filname")
	private String filename;
	
	@OneToOne(mappedBy = "utente", cascade = CascadeType.ALL)
	private Anagrafica anagrafica;
	
}