package com.perigea.tracker.timesheet.utility;

import java.util.UUID;

public class TSUtils {

	private TSUtils() {

	}

	public static final String uuid() {
		return UUID.randomUUID().toString();
	}

}
