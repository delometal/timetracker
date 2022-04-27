package com.perigea.tracker.timesheet.service;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.perigea.tracker.commons.dto.AuthenticationDto;
import com.perigea.tracker.commons.model.AuthCredentials;
import com.perigea.tracker.timesheet.configuration.ApplicationProperties;
import com.perigea.tracker.timesheet.rest.AuthenticationRestClient;

@Service
public class AuthenticationService {

	@Autowired
	private ApplicationProperties properties;

	@Autowired
	private AuthenticationRestClient authRestClient;

	@Autowired
	private Logger logger;

	public AuthenticationDto login(AuthCredentials credentials) {
		try {
			credentials.setGrantType(properties.getGrantType());

			HttpHeaders headers = new HttpHeaders() {
				private static final long serialVersionUID = -6838751019627187913L;

				{
					String auth = properties.getAuthClientUsername() + ":" + properties.getAuthClientPassword();
					byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
					String authHeader = "Basic " + new String(encodedAuth);
					set("Authorization", authHeader);
				}
			};
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("grant_type", credentials.getGrantType());
			map.add("username", credentials.getUsername());
			map.add("password", credentials.getPassword());
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,
					headers);

			return authRestClient.login(request);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}

	}

}
