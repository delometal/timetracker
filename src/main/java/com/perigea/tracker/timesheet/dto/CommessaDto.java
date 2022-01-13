package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enumerator.TipoCommessaType;

import lombok.Data;

@Data
public class CommessaDto  {
	
	private String codiceCommessa;
	private TipoCommessaType tipoCommessaType;
	private String createUser;
	private String lastUpdateUser;
	


}
