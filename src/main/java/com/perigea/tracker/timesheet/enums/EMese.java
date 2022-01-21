package com.perigea.tracker.timesheet.enums;

public enum EMese {
	GEN("GENNAIO", 1, "01"),
	FEB("FEBBRAIO", 2, "02"),
	MAR("MARZO", 3, "03"),
	APR("APRILE", 4, "04"),
	MAG("MAGGIO", 5, "05"),
	GIU("GIUGNO", 6, "06"),
	LUG("LUGLIO", 7, "07"),
	AGO("AGOSTO", 8, "08"),
	SET("SETTEMBRE", 9, "09"),
	OTT("OTTOBRE", 10, "10"),
	NOV("NOVEMBRE", 11, "11"),
	DIC("DICEMBRE", 12, "12");

	EMese(String description, Integer monthId, String monthPart) {
		this.description = description;
		this.monthId = monthId;
		this.monthPart = monthPart;
	}
	
	private String description;
	private Integer monthId;
	private String monthPart;
	
	public String getDescription() {
		return description;
	}
	public Integer getMonthId() {
		return monthId;
	}
	public String getMonthPart() {
		return monthPart;
	}

	public static EMese getByMonthId(Integer mese) {
		for (EMese e : EMese.values()) {
			if(e.getMonthId() == mese) {
				return e;
			}
		}
		return null;
	}
}
