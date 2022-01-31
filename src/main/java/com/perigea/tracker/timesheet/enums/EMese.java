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
	
	public static Integer getDays(Integer monthId, Integer anno) {
		boolean bisestile = anno > 1584 && ((anno % 400 == 0) || (anno % 4 == 0 && anno % 100 != 0));
		for (EMese e : EMese.values()) {
			if(e.getMonthId()== monthId.intValue()) {
				if (e.getMonthId()==2 && bisestile == false) {
					return 28;
				} 	else  if(e.getMonthId()==2 && bisestile == true) {
					return 29; 
				} else  if(e.getMonthId()== 1 || e.getMonthId()==3 || e.getMonthId()==5 || e.getMonthId()==7 || e.getMonthId()==8 || e.getMonthId()==10 || e.getMonthId()==12) {
					return 31;
				} else {
					return 30;
				}
			}
		}
		return null;
	}
}
