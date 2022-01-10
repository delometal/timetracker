package com.perigea.tracker.timesheet.exception;

public class GenericTimesheetException extends RuntimeException {
	private static final long serialVersionUID = 4093329276438371635L;
	
	public GenericTimesheetException(String exception) {
		super(exception);
	}

}