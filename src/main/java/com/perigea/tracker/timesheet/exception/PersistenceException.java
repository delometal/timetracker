package com.perigea.tracker.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PersistenceException extends GenericTimesheetException {

	private static final long serialVersionUID = 5787968918337804222L;

	public PersistenceException(String exception) {
		super(exception);
	}
}
