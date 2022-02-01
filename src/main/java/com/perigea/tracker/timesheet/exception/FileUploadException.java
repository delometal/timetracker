package com.perigea.tracker.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileUploadException extends GenericTimesheetException {

	private static final long serialVersionUID = 8354091153352158194L;

	public FileUploadException(String exception) {
		super(exception);
	}
}
