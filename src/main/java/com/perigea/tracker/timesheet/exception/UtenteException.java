package com.perigea.tracker.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UtenteException extends GenericTimesheetException {

	private static final long serialVersionUID = 2180773863472673454L;

	public UtenteException(String exception) {
		super(exception);
	}
}
