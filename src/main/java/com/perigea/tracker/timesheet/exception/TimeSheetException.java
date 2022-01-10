package com.perigea.tracker.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TimeSheetException extends GenericTimesheetException {

	private static final long serialVersionUID = -4057094117432592811L;

	public TimeSheetException(String exception) {
		super(exception);
	}
}