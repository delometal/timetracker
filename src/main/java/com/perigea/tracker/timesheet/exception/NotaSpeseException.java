package com.perigea.tracker.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotaSpeseException extends GenericTimesheetException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3407464705361255523L;

	public NotaSpeseException(String exception) {
		super(exception);
	}
}
