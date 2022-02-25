package com.perigea.tracker.timesheet.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.perigea.tracker.timesheet.configuration.ApplicationProperties;

@Component
public class CalendarRestClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ApplicationProperties applicationProperties;

	public void sendRichiesta(Object obj, String endpoint) {
		restTemplate.postForObject(applicationProperties.getCalendarConnectionString() + endpoint, obj, getClass());
	}
}
