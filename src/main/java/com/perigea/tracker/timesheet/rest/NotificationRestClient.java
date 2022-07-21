package com.perigea.tracker.timesheet.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.perigea.tracker.commons.dto.BaseDto;
import com.perigea.tracker.commons.dto.CalendarEventDto;
import com.perigea.tracker.commons.dto.NonPersistedEventDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.timesheet.configuration.ApplicationProperties;

@Component
public class NotificationRestClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ApplicationProperties applicationProperties;

	public <T extends CalendarEventDto> void sendNotifica(T event, String endpoint) {
		restTemplate.postForObject(applicationProperties.getCalendarConnectionString() + endpoint, event,
				ResponseDto.class);
	}

	public void sendInstantNotification(NonPersistedEventDto<? extends BaseDto> notifica, String endpoint) {
		restTemplate.postForObject(endpoint, notifica, String.class);
	}

	public <T extends CalendarEventDto> void sendNotificaApprovazione(T event, String endpoint) {
		restTemplate.put(applicationProperties.getCalendarConnectionString() + endpoint, event, ResponseDto.class);
	}

	public void sendNotifica(HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity,
			String endpoint) {
		restTemplate.postForObject(applicationProperties.getCalendarConnectionString() + endpoint, requestEntity,
				ResponseDto.class);
	}

}
