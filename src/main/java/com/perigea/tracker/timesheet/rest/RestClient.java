package com.perigea.tracker.timesheet.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.perigea.tracker.commons.dto.BaseDto;
import com.perigea.tracker.commons.dto.CalendarEventDto;
import com.perigea.tracker.commons.dto.NonPersistedEventDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.commons.model.Email;
import com.perigea.tracker.timesheet.configuration.ApplicationProperties;

@Component
public class RestClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ApplicationProperties applicationProperties;

	public <T extends CalendarEventDto> void sendNotifica(T event, String endpoint) {
		restTemplate.postForObject(applicationProperties.getCalendarConnectionString() + endpoint, event,
				ResponseDto.class);
	}

	public void sendUserNotification(NonPersistedEventDto<? extends BaseDto> notifica) {
		restTemplate.postForObject(applicationProperties.getInstantNotificationEndpoint(), notifica, String.class);
	}

	
}
