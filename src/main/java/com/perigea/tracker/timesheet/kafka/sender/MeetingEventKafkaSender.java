package com.perigea.tracker.timesheet.kafka.sender;

import org.springframework.stereotype.Component;

import com.perigea.tracker.commons.dto.MeetingEventDto;
import com.perigea.tracker.commons.enums.KafkaCalendarEventType;

@Component
public class MeetingEventKafkaSender extends AbstractKafkaSender<MeetingEventDto> {
	
	@Override
	public KafkaCalendarEventType eventType() {
		return KafkaCalendarEventType.MEETING_EVENT_MESSAGE;
	}
	
	@Override
	public Class<MeetingEventDto> entityClass() {
		return MeetingEventDto.class;
	}
	
}
