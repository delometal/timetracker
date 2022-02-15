package com.perigea.tracker.timesheet.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
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
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "codice_persona")
	private Utente utente;
	
	/**
	 * this method allow the usage of the CurriculumRepository in order to directly delete the cv 
	 * without cascading from the utente entity. 
	 * It just synchronize the relation between anagrafica and cv
	 */
	@PreRemove
	private void preRemove() {
		if(utente!=null)
			utente.setCv(null);
	}
	
}