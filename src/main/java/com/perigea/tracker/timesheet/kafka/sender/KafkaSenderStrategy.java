package com.perigea.tracker.timesheet.kafka.sender;


import org.springframework.kafka.core.KafkaTemplate;

import com.perigea.tracker.commons.enums.KafkaCalendarEventType;

public interface KafkaSenderStrategy<T> {
	
	public KafkaCalendarEventType eventType();
	public Class<T> entityClass();
	public KafkaTemplate<String, T> kafkaTemplate();
	public void send(T message);
}
