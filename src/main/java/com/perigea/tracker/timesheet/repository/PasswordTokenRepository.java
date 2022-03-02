package com.perigea.tracker.timesheet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.perigea.tracker.timesheet.entity.PasswordToken;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long>, JpaSpecificationExecutor<PasswordToken> {

	public Optional<PasswordToken> findByUsername(String username);
	
	public Optional<PasswordToken> findByToken(String token);
}
