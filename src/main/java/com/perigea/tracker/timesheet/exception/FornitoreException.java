package com.perigea.tracker.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FornitoreException extends GenericTimesheetException {

	private static final long serialVersionUID = 2411212561597831050L;

	public FornitoreException(String exception) {
		super(exception);
	}
}
