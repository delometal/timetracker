package com.perigea.tracker.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileDownloadException extends GenericTimesheetException {

	private static final long serialVersionUID = -4750823553065612381L;

	public FileDownloadException(String exception) {
		super(exception);
	}
}
