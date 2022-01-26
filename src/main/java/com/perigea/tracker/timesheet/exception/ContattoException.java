package com.perigea.tracker.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ContattoException extends GenericTimesheetException {

	private static final long serialVersionUID = -9200230329643253231L;

	public ContattoException(String exception) {
		super(exception);
	}
}