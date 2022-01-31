package com.perigea.tracker.timesheet.utility;

import java.time.LocalDate;
import java.util.UUID;

public class TSUtils {

	private TSUtils() {

	}

	public static final String uuid() {
		return UUID.randomUUID().toString();
	}
	
	public static final LocalDate now() {
		return LocalDate.now();
	}
	
}
