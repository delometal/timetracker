package com.perigea.tracker.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConsulenteException extends GenericTimesheetException {

	private static final long serialVersionUID = 1754465139558061307L;

	public ConsulenteException(String exception) {
		super(exception);
	}
}
