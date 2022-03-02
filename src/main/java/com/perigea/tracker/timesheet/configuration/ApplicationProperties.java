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

	@Value("${perigea.system.uploads.dir}")
	private String uploadDirectory;
	
	@Value("${perigea.system.curriculum.dir}")
	private String curriculumDiskDir;
	
	@Value("${perigea.system.curriculum.disk.persistence:false}")
	private boolean curriculumDiskPersistence;
	
	@Value("${calendar.connection}")
	private String calendarConnectionString;
	
	@Value("${spring.mail.username}")
	private String sender;
	
	@Value("${notificator.endpoint}")
	private String notificatorEndpoint;
		
}