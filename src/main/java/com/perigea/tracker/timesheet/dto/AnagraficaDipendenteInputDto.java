package com.perigea.tracker.timesheet.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class AnagraficaDipendenteInputDto extends AnagraficaDipendenteDto {

	private static final long serialVersionUID = -8948320302365869807L;
	
	private UtentePostDto utenteDto;

}
