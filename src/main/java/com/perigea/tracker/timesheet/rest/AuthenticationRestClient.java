package com.perigea.tracker.timesheet.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.perigea.tracker.commons.dto.AuthenticationDto;
import com.perigea.tracker.timesheet.configuration.ApplicationProperties;

@Component
public class AuthenticationRestClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ApplicationProperties properties;

	public AuthenticationDto login(HttpEntity<MultiValueMap<String, String>> request) {
		return restTemplate.postForObject(properties.getOauthTokenEndpoint(), request, AuthenticationDto.class);
	}
}
