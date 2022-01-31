package com.perigea.tracker.timesheet.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseDto implements Serializable {

	private static final long serialVersionUID = 3796489766288029593L;

	private String createUser;
	private String lastUpdateUser;
	private LocalDate createTimestamp;
	private LocalDate lastUpdateTimestamp;
	
}
