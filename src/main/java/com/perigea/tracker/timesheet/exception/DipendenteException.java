package com.perigea.tracker.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DipendenteException extends GenericTimesheetException {

	private static final long serialVersionUID = 6417192002065026915L;

	public DipendenteException(String exception) {
		super(exception);
	}

}