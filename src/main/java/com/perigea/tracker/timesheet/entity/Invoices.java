package com.perigea.tracker.timesheet.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Invoices {

	private Integer itemId;
	private String itemName;
	private Integer itemQty;
	private Double totalPrice;
	private Date itemSoldDate;
	
	public Invoices(Integer itemId, String itemName, Integer itemQty, Double totalPrice, Date itemSoldDate) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemQty = itemQty;
		this.totalPrice = totalPrice;
		this.itemSoldDate = itemSoldDate;
	}
	
	

}