package com.perigea.tracker.timesheet.dto;

import java.util.Date;

import com.perigea.tracker.timesheet.enumerator.TipoCommessaType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommessaDto  {
	
	private String codiceCommessa;
	private TipoCommessaType tipoCommessaType;
	private String createUser;
	private String lastUpdateUser;
	
	public CommessaDto() {
		super();
	}
	

}
