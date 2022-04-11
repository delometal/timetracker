package com.perigea.tracker.timesheet.kafka.sender;

import org.springframework.stereotype.Component;

import com.perigea.tracker.commons.dto.TimesheetEventDto;
import com.perigea.tracker.commons.enums.KafkaCalendarEventType;

@Component
public class TimesheetEventKafkaSender extends AbstractKafkaSender<TimesheetEventDto> {

	@Override
	public KafkaCalendarEventType eventType() {
		return KafkaCalendarEventType.TIMESHEET_EVENT_MESSAGE;
	}
	
	@Override
	public Class<TimesheetEventDto> entityClass() {
		return TimesheetEventDto.class;
	}
	
}
