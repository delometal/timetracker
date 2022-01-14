package com.perigea.tracker.timesheet.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@DiscriminatorValue("NF")
@EqualsAndHashCode(callSuper = true)
public class CommessaNonFatturabile extends Commessa {

	private static final long serialVersionUID = 2256677862368637963L;

}