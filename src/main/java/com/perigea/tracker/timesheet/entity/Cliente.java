package com.perigea.tracker.timesheet.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@DiscriminatorValue("C")
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Azienda {

	private static final long serialVersionUID = -453310526859072695L;

	@OneToMany(mappedBy = "cliente")
	private List<CommessaFatturabile> commesse = new ArrayList<>();

	@OneToMany(mappedBy = "cliente")
	private List<OrdineCommessa> ordiniCommesse = new ArrayList<>();
	
}
