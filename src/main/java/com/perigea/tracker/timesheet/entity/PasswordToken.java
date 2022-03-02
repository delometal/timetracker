package com.perigea.tracker.timesheet.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.perigea.tracker.commons.enums.PasswordTokenStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "password")
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PasswordToken extends BaseEntity {

	private static final long serialVersionUID = 1643472222687806842L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "token", unique = true)
	private String token;

	@Column(name = "data_scadenza")
	private LocalDateTime dataScadenza;

//	@Column(name = "token_status")
//	@Enumerated(EnumType.STRING)
//	private PasswordTokenStatus tokenStatus;
	
//	public PasswordToken(PasswordTokenStatus status) {
//		this.setTokenStatus(PasswordTokenStatus.QUALIFIED);
//	}
	
}
