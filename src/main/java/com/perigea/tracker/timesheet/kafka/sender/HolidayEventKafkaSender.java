package com.perigea.tracker.timesheet.kafka.sender;


import org.springframework.stereotype.Component;

import com.perigea.tracker.commons.dto.HolidayEventRequestDto;
import com.perigea.tracker.commons.enums.KafkaCalendarEventType;

@Component
public class HolidayEventKafkaSender extends AbstractKafkaSender<HolidayEventRequestDto> {

	@Override
	public KafkaCalendarEventType eventType() {
		return KafkaCalendarEventType.HOLIDAY_EVENT_MESSAGE;
	}
	
	@Override
	public Class<HolidayEventRequestDto> entityClass() {
		return HolidayEventRequestDto.class;
	}
	
}
