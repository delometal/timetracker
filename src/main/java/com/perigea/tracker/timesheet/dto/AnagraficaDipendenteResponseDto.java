package com.perigea.tracker.timesheet.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class AnagraficaDipendenteResponseDto extends AnagraficaDipendenteDto {

	private static final long serialVersionUID = -3519519745492918496L;
	
	private UtenteViewDto utenteDto;

}
