package com.perigea.tracker.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GenericTimesheetException extends RuntimeException {
	private static final long serialVersionUID = 4093329276438371635L;
	
	public GenericTimesheetException(String exception) {
		super(exception);
	}

}