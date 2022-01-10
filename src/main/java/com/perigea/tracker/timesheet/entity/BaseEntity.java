package com.perigea.tracker.timesheet.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;


public class BaseEntity {
	
	@Column(name = "create_timestamp")
	private Date createTimestamp;

	@Column(name = "last_update_timestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "create_user")
	private String createUser;

	@Column(name = "last_update_user")
	private String lastUpdateUser;

	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public Date getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(Date lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	
	

}
