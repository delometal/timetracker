package com.perigea.tracker.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RuoloException extends GenericTimesheetException {

	private static final long serialVersionUID = -3872210085595672956L;

	public RuoloException(String exception) {
		super(exception);
	}
}