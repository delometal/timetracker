package com.perigea.tracker.timesheet.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UtentePostDto extends UtenteBaseDto {

	private static final long serialVersionUID = 2639490889649850289L;
	
	private String password;

}
