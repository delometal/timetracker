package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.perigea.tracker.timesheet.entity.Anagrafica;
import com.perigea.tracker.timesheet.entity.Gruppo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class GruppoContattoKey implements Serializable {

	private static final long serialVersionUID = -2275333400404753544L;
	
	@Column(name = "id_contatto", nullable = false)
	private Integer codiceContatto;
	
	@Column(name = "id_gruppo", nullable = false)
	private Integer codiceGruppo;
	
	@ManyToOne
	@JoinColumn(name = "id_contatto", referencedColumnName = "id_contatto", updatable = false, insertable = false)
	private Anagrafica contatto;

	@ManyToOne
	@JoinColumn(name = "id_gruppo", referencedColumnName = "id_gruppo", updatable = false, insertable = false)
	private Gruppo gruppo;

	public GruppoContattoKey(Integer codiceContatto, Integer codiceGruppo) {
		super();
		this.codiceContatto = codiceContatto;
		this.codiceGruppo = codiceGruppo;
	}
	
}