package com.perigea.tracker.timesheet.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -2110635013142689748L;

	@Column(name = "create_timestamp")
	private LocalDateTime createTimestamp;

	@Column(name = "last_update_timestamp")
	private LocalDateTime lastUpdateTimestamp;

	@Column(name = "create_user")
	private String createUser;

	@Column(name = "last_update_user")
	private String lastUpdateUser;
	
	@PrePersist
	public void prePersist() {
		if(createTimestamp == null) {
			createTimestamp = LocalDateTime.now();			
		}
	}
	
	@PreUpdate
	public void preUpdate() {
		lastUpdateTimestamp = LocalDateTime.now();
	}

}
