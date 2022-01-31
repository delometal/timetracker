package com.perigea.tracker.timesheet.utility;

import java.time.LocalDate;
import java.util.UUID;

public class TSUtils {
	
	public static final String EXCEL_EXT = ".xlsx";
	public static final String PERIGEA_LOGO_COLOR = "\\images\\perigea_color_logo.png";
	public static final String PERIGEA_LOGO_WHITE = "\\images\\perigea_white_logo.png";

	private TSUtils() {

	}

	public static final String uuid() {
		return UUID.randomUUID().toString();
	}
	
	public static final LocalDate now() {
		return LocalDate.now();
	}
	
	public static final String removeAllSpaces(String value) {
		return value.replaceAll("\\s+", "");
	}
	
}