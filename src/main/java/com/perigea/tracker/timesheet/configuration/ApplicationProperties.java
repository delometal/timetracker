package com.perigea.tracker.timesheet.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@PropertySource("classpath:/application-${spring.profiles.active}.properties")
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
	
	@Value("${calendar.notification.userCreated}")
	private String userCreationNotificationEndpoint;
	
	@Value("${calendar.notification.avvisoBacheca}")
	private String avvisoBachecaEndpoint;
	
	@Value("${notificator.endpoint}")
	private String notificatorEndpoint;
	
	@Value("${scheduler.notifica.endpoint}")
	private String schedulerEndpoint;
	
	@Value("${password.endpoint}")
	private String passwordUpdateEndpoint;
	
	@Value("${authServer.token.endpoint}")
	private String oauthTokenEndpoint;
	
	@Value("${grant.type}")
	private String grantType;
	
	@Value("${authorization.username}")
	private String authClientUsername;
	
	@Value("${authorization.password}")
	private String authClientPassword;
}