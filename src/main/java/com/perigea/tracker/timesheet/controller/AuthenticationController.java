package com.perigea.tracker.timesheet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.commons.dto.AuthenticationDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.commons.model.AuthCredentials;
import com.perigea.tracker.timesheet.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authService;

	@PostMapping(value = "/login")
	public ResponseEntity<ResponseDto<AuthenticationDto>> login(@RequestBody AuthCredentials credentials) {
		AuthenticationDto auth = authService.login(credentials);
		ResponseDto<AuthenticationDto> genericResponse = ResponseDto.<AuthenticationDto>builder().data(auth).build();
		return ResponseEntity.ok(genericResponse);
	}
}
