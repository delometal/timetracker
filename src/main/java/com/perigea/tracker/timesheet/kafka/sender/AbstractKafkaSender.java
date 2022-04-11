package com.perigea.tracker.timesheet.kafka.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.perigea.tracker.commons.enums.KafkaCalendarEventType;

public abstract class AbstractKafkaSender<T> implements KafkaSenderStrategy<T> {

	@Autowired
	private KafkaTemplate<String, T> kafkaTemplate;

	@Override
	public abstract KafkaCalendarEventType eventType();
	
	@Override
	public abstract Class<T> entityClass();
	
	@Override
	public KafkaTemplate<String, T> kafkaTemplate() {
		return kafkaTemplate;
	}

	@Override
	public void send(T message) {
		kafkaTemplate().send(eventType().getTopic(), message);
	}

}
