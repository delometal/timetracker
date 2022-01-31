package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enums.AnagraficaType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ContattoDto extends AnagraficaDto {

	private static final long serialVersionUID = -1157857116466220981L;
	
	private AnagraficaType tipo = AnagraficaType.C;

}
