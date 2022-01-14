package com.perigea.tracker.timesheet.enumerator;

public enum PagamentoType {
	
	A("30GG_DF"),
	B("30GG_FMDF"),
	C("60GG_DF"),
	D("60GG_FMDF"),
	E("90GG_DF"),
	F("90GG_FMDF"),
	G("120GG_DF"),
	H("120GG_FMDF");

	PagamentoType(String descrizione) {
		this.descrizione = descrizione;
	}
	
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}
	
}
