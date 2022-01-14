package com.perigea.tracker.timesheet.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UtenteViewDto extends UtenteBaseDto {

	private static final long serialVersionUID = 8700786798826343452L;

	private UtenteViewDto responsabileDto;
	
}
