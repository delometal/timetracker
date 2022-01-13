package com.perigea.tracker.timesheet.wrapper;

import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteDto;
import com.perigea.tracker.timesheet.dto.UtentePostDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnagraficaDipendenteWrapper {
	private AnagraficaDipendenteDto dipendenteDto;
	private UtentePostDto utenteDto;
	private String codiceResponsabile;


}
