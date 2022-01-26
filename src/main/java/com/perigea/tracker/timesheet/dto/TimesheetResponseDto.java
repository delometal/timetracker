package com.perigea.tracker.timesheet.dto;

import java.util.List;

import com.perigea.tracker.timesheet.enums.StatoRichiestaType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TimesheetResponseDto extends BaseDto {
	
	private static final long serialVersionUID = -4618598347765968980L;
	
	private String codicePersona;
	private Integer anno;
	private Integer mese;
	private Integer oreTotali;
	private StatoRichiestaType statoRichiesta;
	private List<TimesheetEntryDto> entries;
	private String createUser;
	private String lastUpdateUser;
	private String nome;
	private String cognome;
	private String mailAziendale;
	
}