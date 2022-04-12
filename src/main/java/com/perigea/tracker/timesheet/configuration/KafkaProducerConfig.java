package com.perigea.tracker.timesheet.configuration;

import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;

import com.perigea.tracker.commons.dto.HolidayEventRequestDto;
import com.perigea.tracker.commons.dto.MeetingEventDto;
import com.perigea.tracker.commons.dto.TimesheetEventDto;

@Configuration
public class KafkaProducerConfig {
	
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Bean
    @Primary
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
	
	@Bean("timesheetMessageProducerFactory")
	public ProducerFactory<String, TimesheetEventDto> timesheetMessageProducerFactory() {
		Map<String, Object> producerProperties = applicationProperties.getKafkaProperties().buildProducerProperties();
		producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
		DefaultKafkaProducerFactory<String, TimesheetEventDto> factory = new DefaultKafkaProducerFactory<>(producerProperties);
		String transactionIdPrefix = applicationProperties.getKafkaProperties().getProducer().getTransactionIdPrefix();
		if (transactionIdPrefix != null) {
			factory.setTransactionIdPrefix(transactionIdPrefix + "-timesheetMessage");
		}
		return factory;
	}
	
	@Bean("timesheetMessageKafkaTemplate")
	public KafkaTemplate<String, TimesheetEventDto> timesheetMessageKafkaTemplate(){
		return new KafkaTemplate<>(timesheetMessageProducerFactory());
	}
	
	@Bean("timesheetKafkaTransactionManager")
	public KafkaTransactionManager<String, TimesheetEventDto> timesheetKafkaTransactionManager(){
		return new KafkaTransactionManager<>(timesheetMessageProducerFactory());
	}

	@Bean("holidayMessageProducerFactory")
	public ProducerFactory<String, HolidayEventRequestDto> holidayMessageProducerFactory() {
		Map<String, Object> producerProperties = applicationProperties.getKafkaProperties().buildProducerProperties();
		producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
		DefaultKafkaProducerFactory<String, HolidayEventRequestDto> factory = new DefaultKafkaProducerFactory<>(producerProperties);
		String transactionIdPrefix = applicationProperties.getKafkaProperties().getProducer().getTransactionIdPrefix();
		if (transactionIdPrefix != null) {
			factory.setTransactionIdPrefix(transactionIdPrefix + "-holidayRequestMessage");
		}
		return factory;
	}
	
	@Bean("holidayMessageKafkaTemplate")
	public KafkaTemplate<String, HolidayEventRequestDto> holidayMessageKafkaTemplate(){
		return new KafkaTemplate<>(holidayMessageProducerFactory());
	}
	
	@Bean("holidayKafkaTransactionManager")
	public KafkaTransactionManager<String, HolidayEventRequestDto> holidayKafkaTransactionManager(){
		return new KafkaTransactionManager<String, HolidayEventRequestDto>(holidayMessageProducerFactory());
	}
	
	@Bean("riunioneMessageProducerFactory")
	public ProducerFactory<String, MeetingEventDto> riunioneMessageProducerFactory() {
		Map<String, Object> producerProperties = applicationProperties.getKafkaProperties().buildProducerProperties();
		producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
		DefaultKafkaProducerFactory<String, MeetingEventDto> factory = new DefaultKafkaProducerFactory<>(producerProperties);
		String transactionIdPrefix = applicationProperties.getKafkaProperties().getProducer().getTransactionIdPrefix();
		if (transactionIdPrefix != null) {
			factory.setTransactionIdPrefix(transactionIdPrefix + "-meetingMessage");
		}
		return factory;
	}
	
	
	@Bean("meetingMessageKafkaTemplate")
	public KafkaTemplate<String, MeetingEventDto> meetingMessageKafkaTemplate(){
		return new KafkaTemplate<>(riunioneMessageProducerFactory());
	}
	
	@Bean("meetingKafkaTransactionManager")
	public KafkaTransactionManager<String, MeetingEventDto> meetingKafkaTransactionManager(){
		return new KafkaTransactionManager<String, MeetingEventDto>(riunioneMessageProducerFactory());
	}
}
