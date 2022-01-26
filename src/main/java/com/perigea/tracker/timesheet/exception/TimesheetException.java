package com.perigea.tracker.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TimesheetException extends GenericTimesheetException {

	private static final long serialVersionUID = -4057094117432592811L;

	public TimesheetException(String exception) {
		super(exception);
	}
}