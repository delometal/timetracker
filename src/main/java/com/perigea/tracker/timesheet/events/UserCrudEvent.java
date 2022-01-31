package com.perigea.tracker.timesheet.events;

import org.springframework.context.ApplicationEvent;

import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteResponseDto;
import com.perigea.tracker.timesheet.enums.CrudType;

public class UserCrudEvent extends ApplicationEvent {

	private static final long serialVersionUID = -3127678851169887814L;
	private CrudType crudType;
	private AnagraficaDipendenteResponseDto anagraficaDto;
	
	public UserCrudEvent(Object source, AnagraficaDipendenteResponseDto anagraficaDto, CrudType crudType) {
		super(source);
		this.crudType = crudType;
		this.anagraficaDto = anagraficaDto;
	}

	public CrudType getCrudType() {
		return crudType;
	}

	public void setCrudType(CrudType crudType) {
		this.crudType = crudType;
	}

	public AnagraficaDipendenteResponseDto getAnagraficaDto() {
		return anagraficaDto;
	}

	public void setAnagraficaDto(AnagraficaDipendenteResponseDto anagraficaDto) {
		this.anagraficaDto = anagraficaDto;
	}

}
