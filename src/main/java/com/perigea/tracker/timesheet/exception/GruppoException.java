package com.perigea.tracker.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GruppoException extends GenericTimesheetException {

	private static final long serialVersionUID = 5313588146595746895L;

	public GruppoException(String exception) {
		super(exception);
	}
}