package com.perigea.tracker.timesheet.kafka.sender;

import org.springframework.stereotype.Component;

import com.perigea.tracker.commons.dto.MeetingEventDto;
import com.perigea.tracker.commons.enums.KafkaCalendarEventType;

@Component
public class RiunioneEventKafkaSender extends AbstractKafkaSender<MeetingEventDto> {
	
	@Override
	public KafkaCalendarEventType eventType() {
		return KafkaCalendarEventType.RIUNIONE_EVENT_MESSAGE;
	}
	
	@Override
	public Class<MeetingEventDto> entityClass() {
		return MeetingEventDto.class;
	}
	
}
