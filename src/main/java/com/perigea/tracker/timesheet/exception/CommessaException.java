package com.perigea.tracker.timesheet.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CommessaException extends GenericTimesheetException {

	private static final long serialVersionUID = 1664471807917256261L;

	public CommessaException(String exception) {
		super(exception);
	}
}