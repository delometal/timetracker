package com.perigea.tracker.timesheet.dto;

import java.util.ArrayList;
import java.util.List;

import com.perigea.tracker.commons.enums.CommessaType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TimesheetEntryDto extends BaseDto {
	
	private static final long serialVersionUID = 8273187903186747431L;

	private String codiceCommessa;
	private Integer giorno;
	private Integer ore;
	private Boolean trasferta;
	private CommessaType tipoCommessa;
	private String createUser;
	private String lastUpdateUser;
	private List<NotaSpeseDto> noteSpesa = new ArrayList<>();
	private String descrizioneCommessa;
	private String ragioneSociale;
	
}