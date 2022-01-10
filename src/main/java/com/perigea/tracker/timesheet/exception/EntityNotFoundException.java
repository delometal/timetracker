package com.perigea.tracker.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends GenericTimesheetException {

	private static final long serialVersionUID = 1664471807917256261L;

	public EntityNotFoundException(String exception) {
		super(exception);
	}
}
