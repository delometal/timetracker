package com.perigea.tracker.timesheet.kafka.sender;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.dto.CalendarEventDto;
import com.perigea.tracker.commons.dto.HolidayEventRequestDto;
import com.perigea.tracker.commons.dto.MeetingEventDto;
import com.perigea.tracker.commons.dto.TimesheetEventDto;

@Component("kafkaSender")
public class KafkaSender {

	@Autowired
	private Logger logger;

	@Autowired
	private KafkaSenderStrategyFactory<TimesheetEventDto> kafkaTimesheetStrategyFactory;

	@Autowired
	private KafkaSenderStrategyFactory<HolidayEventRequestDto> kafkaHolidayStrategyFactory;

	@Autowired
	private KafkaSenderStrategyFactory<MeetingEventDto> kafkaRiunioneStrategyFactory;
	
	@Autowired
	@Qualifier("kafkaAdminClient")
	private AdminClient kafkaAdminClient;

	private static final int CHECK_TIMEOUT = 2000;	
	
	@Transactional(transactionManager = "timesheetKafkaTransactionManager")
	private void send(TimesheetEventDto message) {
		TimesheetEventKafkaSender sender = (TimesheetEventKafkaSender) kafkaTimesheetStrategyFactory
				.load(TimesheetEventDto.class);
		sender.send(message);
	}

	@Transactional(transactionManager = "holidayKafkaTransactionManager")
	private void send(HolidayEventRequestDto message) {
		HolidayEventKafkaSender sender = (HolidayEventKafkaSender) kafkaHolidayStrategyFactory
				.load(HolidayEventRequestDto.class);
		sender.send(message);
	}

	@Transactional(transactionManager = "meetingKafkaTransactionManager")
	private void send(MeetingEventDto message) {
		MeetingEventKafkaSender sender = (MeetingEventKafkaSender) kafkaRiunioneStrategyFactory
				.load(MeetingEventDto.class);
		sender.send(message);
	}

	// FIXME switch no su stringa ma su classType
	@Transactional
	public void send(CalendarEventDto message) {
		Class<? extends CalendarEventDto> clazz = message.getClass();
		String clazzType = clazz.getSimpleName();
		switch (clazzType) {
		case "TimesheetEventDto":
			send((TimesheetEventDto) message);
			break;
		case "HolidayEventRequestDto":
			send((HolidayEventRequestDto) message);
			break;
		case "MeetingEventDto":
			send((MeetingEventDto) message);
			break;
		default:
			break;
		}
	}

	public boolean isBrokerOn() {
		
		try {
			kafkaAdminClient.listTopics(new ListTopicsOptions().timeoutMs(CHECK_TIMEOUT)).listings().get();
		} catch (ExecutionException | InterruptedException ex) {
			logger.error("Kafka is not available, timed out after {} ms", CHECK_TIMEOUT);
			return false;
		}
		return true;
	}
}
