package com.perigea.tracker.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AnagraficaException extends GenericTimesheetException {

	private static final long serialVersionUID = -926475465752002328L;

	public AnagraficaException(String exception) {
		super(exception);
	}
}
