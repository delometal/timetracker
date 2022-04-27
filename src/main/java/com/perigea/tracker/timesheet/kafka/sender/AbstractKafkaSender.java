package com.perigea.tracker.timesheet.kafka.sender;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.perigea.tracker.commons.enums.KafkaCalendarEventType;

import lombok.Data;

@Data
public abstract class AbstractKafkaSender<T> implements KafkaSenderStrategy<T> {

	private static AtomicBoolean isActive = new AtomicBoolean(true);
	
	@Autowired
	private Logger logger;
	
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
		if (isActive.get()) {
			kafkaTemplate.send(eventType().getTopic(), message);
		} else {
			logger.info("Unable to send {}", message);
		}
//		ListenableFuture<SendResult<String, T>> future = this.
//		future.addCallback(new ListenableFutureCallback<>() {
//	        @Override
//	        public void onFailure(Throwable ex) {
//	            logger.error("Unable to send message=[ {} ] due to : {}", message, ex.getMessage());
//	        }
//	        
//	        @Override
//	        public void onSuccess(SendResult<String, T> result) {
//	            logger.error("Sent message=[ {} ] with offset=[ {} ]", message, result.getRecordMetadata().offset());
//	        }
//
//	    });
	}

}
