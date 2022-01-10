package com.perigea.tracker.timesheet.wrapper;

import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteDto;
import com.perigea.tracker.timesheet.dto.UtenteDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnagraficaDipendenteWrapper {
	private AnagraficaDipendenteDto dipendenteDto;
	private UtenteDto utenteDto;
	private String codiceResponsabile;


}
