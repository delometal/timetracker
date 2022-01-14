package com.perigea.tracker.timesheet.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@PropertySource("classpath:/application.properties")
public class ApplicationProperties {

	@Value("${perigea.data.partita-iva}")
	private String partitaIvaPerigea;

}