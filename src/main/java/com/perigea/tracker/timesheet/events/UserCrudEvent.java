package com.perigea.tracker.timesheet.events;

import org.springframework.context.ApplicationEvent;

import com.perigea.tracker.timesheet.dto.DipendenteDto;
import com.perigea.tracker.timesheet.enums.CrudType;

public class UserCrudEvent extends ApplicationEvent {

	private static final long serialVersionUID = -3127678851169887814L;
	private CrudType crudType;
	private DipendenteDto dipendente;
	
	public UserCrudEvent(Object source, DipendenteDto dipendente, CrudType crudType) {
		super(source);
		this.crudType = crudType;
		this.dipendente = dipendente;
	}

	public CrudType getCrudType() {
		return crudType;
	}

	public void setCrudType(CrudType crudType) {
		this.crudType = crudType;
	}

	public DipendenteDto getDipendente() {
		return dipendente;
	}

	public void setDipendenteDto(DipendenteDto dipendente) {
		this.dipendente = dipendente;
	}

}
