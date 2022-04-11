package com.perigea.tracker.timesheet.kafka.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.dto.CalendarEventDto;
import com.perigea.tracker.commons.dto.HolidayEventRequestDto;
import com.perigea.tracker.commons.dto.MeetingEventDto;
import com.perigea.tracker.commons.dto.TimesheetEventDto;

@Component("kafkaSender")
public class KafkaSender {

	@Autowired
	private KafkaSenderStrategyFactory<TimesheetEventDto> kafkaTimesheetStrategyFactory;
	
	@Autowired
	private KafkaSenderStrategyFactory<HolidayEventRequestDto> kafkaHolidayStrategyFactory;
	
	@Autowired
	private KafkaSenderStrategyFactory<MeetingEventDto> kafkaRiunioneStrategyFactory;
	
	@Transactional(transactionManager = "timesheetKafkaTransactionManager")
	private void send(TimesheetEventDto message) {
		TimesheetEventKafkaSender sender = (TimesheetEventKafkaSender) kafkaTimesheetStrategyFactory.load(TimesheetEventDto.class);
		sender.send(message);
	}
	
	@Transactional(transactionManager = "holidayKafkaTransactionManager")
	private void send(HolidayEventRequestDto message) {
		HolidayEventKafkaSender sender = (HolidayEventKafkaSender) kafkaHolidayStrategyFactory.load(HolidayEventRequestDto.class);
		sender.send(message);
	}
	
	@Transactional(transactionManager = "meetingKafkaTransactionManager")
	private void send(MeetingEventDto message) {
		RiunioneEventKafkaSender sender = (RiunioneEventKafkaSender) kafkaRiunioneStrategyFactory.load(MeetingEventDto.class);
		sender.send(message);
	}
	
	public void send(CalendarEventDto message) {
		Class<? extends CalendarEventDto> clazz = message.getClass();
		String clazzType = clazz.getName();
		
		switch(clazzType) {
			case "TimesheetEventDto":
				send((TimesheetEventDto) message);
				break;
			case "HolidayEventRequestDto":
				send((HolidayEventRequestDto) message);
				break;
			case "MeetingEventDto":
				send((MeetingEventDto) message);
				break;
			default: break;
		}
			
	}
}
