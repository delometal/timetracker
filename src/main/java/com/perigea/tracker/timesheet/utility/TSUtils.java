package com.perigea.tracker.timesheet.utility;

import java.util.UUID;

public class TSUtils {
	
	public static final String EXCEL_EXT = ".xlsx";

	private TSUtils() {

	}

	public static final String uuid() {
		return UUID.randomUUID().toString();
	}
	
}
