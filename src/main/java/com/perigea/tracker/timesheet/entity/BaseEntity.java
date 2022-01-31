package com.perigea.tracker.timesheet.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;

import lombok.Data;

@Data
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -2110635013142689748L;

	@Column(name = "create_timestamp")
	private LocalDate createTimestamp;

	@Column(name = "last_update_timestamp")
	private LocalDate lastUpdateTimestamp;

	@Column(name = "create_user")
	private String createUser;

	@Column(name = "last_update_user")
	private String lastUpdateUser;

}
