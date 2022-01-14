package com.perigea.tracker.timesheet.utility;

import java.util.Date;
import java.util.UUID;

public class TSUtils {

	private TSUtils() {

	}

	public static final String uuid() {
		return UUID.randomUUID().toString();
	}
	
	public static final Date newDate() {
		return new Date();
	}

}
