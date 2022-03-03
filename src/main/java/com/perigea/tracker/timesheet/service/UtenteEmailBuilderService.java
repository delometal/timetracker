package com.perigea.tracker.timesheet.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.commons.enums.EmailTemplates;
import com.perigea.tracker.commons.enums.EmailType;
import com.perigea.tracker.commons.model.Email;
import com.perigea.tracker.timesheet.configuration.ApplicationProperties;
import com.perigea.tracker.timesheet.entity.PasswordToken;
import com.perigea.tracker.timesheet.entity.Utente;

@Service
public class UtenteEmailBuilderService {

	@Autowired
	private ApplicationProperties applicationProperties;

	private static final String PATTERN = "dd-MM-yyyy HH:mm";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(PATTERN);

	// FIXME non hardocded
	private static final String PASSWORD_UPDATE_ENDPOINT = "https://localhost:9094/timesheet/api/check-token";

	public Email buildCredential(PasswordToken token, Utente utente, String password) throws URISyntaxException {
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("ECT"));
		List<String> recipients = new ArrayList<>();
		Map<String, Object> templateData = new HashMap<>();

//		if (!NotNullValidator.validate(token))
//			throw new NullFieldException(String.format("%s must not be null!", NotNullValidator.getDetails(token)));
//		if (!NotNullValidator.validate(utente))
//			throw new NullFieldException(String.format("%s must not be null!", NotNullValidator.getDetails(utente)));

		recipients.add(utente.getMailAziendale());

		templateData.put("utente", utente.getNome());
		templateData.put("username", utente.getUsername());
		templateData.put("password", password);
		templateData.put("token", token.getToken());
		templateData.put("scadenza", token.getDataScadenza());
		templateData.put("link", createTokenLink(token.getToken()));

		return Email.builder().eventId("creazione nuovo utente").from(applicationProperties.getSender())
				.templateName(EmailTemplates.CREATE_CREDENTIAL_TEMPLATE.getDescrizione()).templateModel(templateData).subject("Attivazione credenziali")
				.emailType(EmailType.HTML_TEMPLATE_MAIL).to(recipients).build();
	}

	
	public Email buildCredentialReminder(PasswordToken token, Utente utente, Integer ore) {
		List<String> recipients = new ArrayList<>();
		Map<String, Object> templateData = new HashMap<>();

//		if (!NotNullValidator.validate(token))
//			throw new NullFieldException(String.format("%s must not be null!", NotNullValidator.getDetails(token)));
//		if (!NotNullValidator.validate(utente))
//			throw new NullFieldException(String.format("%s must not be null!", NotNullValidator.getDetails(utente)));

		recipients.add(utente.getMailAziendale());

		templateData.put("username", utente.getUsername());
		templateData.put("ore", ore);
		try {
			templateData.put("link", createTokenLink(token.getToken()));
		} catch (URISyntaxException e) {

			e.printStackTrace();
		}

		return Email.builder().eventId("reminder attivazione credenziali").from(applicationProperties.getSender())
				.templateName(EmailTemplates.REMINDER_CREDENTIAL_TEMPLATE.getDescrizione()).templateModel(templateData)
				.subject("Attivazione credenziali").emailType(EmailType.HTML_TEMPLATE_MAIL).to(recipients).build();
	}

	public URI createTokenLink(String token) throws URISyntaxException {
		return new URI(PASSWORD_UPDATE_ENDPOINT + "/" + token);
	}
}
