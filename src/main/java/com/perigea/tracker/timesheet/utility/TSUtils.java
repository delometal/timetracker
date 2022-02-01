package com.perigea.tracker.timesheet.utility;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public static final boolean isEmpty(final String x) {
		return (x == null || x.trim().isEmpty());
	}
	
	public static final boolean isEmpty(final Object x) {
		if (x == null) return true;
		if (TSUtils.isPrimitiveOrPrimitiveWrapperOrString(x.getClass())) return TSUtils.isEmpty(x.toString());
		return false;
	}

	public static final boolean isEmpty(final String[] x) {
		return (x == null || x.length == 0);
	}

	public static final boolean isEmpty(final Collection<?> x) {
		return (x == null || x.isEmpty());
	}

	public static final <T1, T2> boolean isEmpty(final Map<T1, T2> map) {
		return (map == null || map.isEmpty());
	}

	public static final boolean containsSpace(final String x) {
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(x);
		return matcher.find();
	}

	public static final boolean isPrimitiveOrPrimitiveWrapperOrString(final Class<?> type) {
		return (type.isPrimitive() && type != void.class) || type == Double.class || type == Float.class
				|| type == Long.class || type == Integer.class || type == Short.class || type == Character.class
				|| type == Byte.class || type == Boolean.class || type == String.class;
	}

	
}