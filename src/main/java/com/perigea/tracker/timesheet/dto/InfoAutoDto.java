package com.perigea.tracker.timesheet.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class InfoAutoDto implements Serializable {
	
	private static final long serialVersionUID = -1120652053306964173L;
	private String modelloAuto;
	private Float rimborsoPerKm;
	private Float kmPerGiorno;
	
}
