package com.perigea.tracker.timesheet.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.commons.enums.EmailTemplates;
import com.perigea.tracker.commons.enums.EmailType;
import com.perigea.tracker.commons.exception.NullFieldException;
import com.perigea.tracker.commons.exception.URIException;
import com.perigea.tracker.commons.model.Email;
import com.perigea.tracker.commons.utils.NotNullValidator;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.configuration.ApplicationProperties;
import com.perigea.tracker.timesheet.entity.PasswordToken;
import com.perigea.tracker.timesheet.entity.Utente;

@Service
public class UtenteEmailBuilderService {

	@Autowired
	private ApplicationProperties applicationProperties;

	public Email buildCredential(PasswordToken token, Utente utente, String password) throws URISyntaxException {
		Utils.DATE_FORMATTER.setTimeZone(TimeZone.getTimeZone("ECT"));
		List<String> recipients = new ArrayList<>();
		Map<String, Object> templateData = new HashMap<>();

		if (!NotNullValidator.validate(token))
			throw new NullFieldException(String.format("%s must not be null!", NotNullValidator.getDetails(token)));
		if (!NotNullValidator.validate(utente))
			throw new NullFieldException(String.format("%s must not be null!", NotNullValidator.getDetails(utente)));

		recipients.add(utente.getMailAziendale());

		templateData.put("utente", utente.getNome());
		templateData.put("username", utente.getUsername());
		templateData.put("password", password);
		templateData.put("token", token.getToken());
		templateData.put("scadenza", token.getDataScadenza());
		templateData.put("link", createTokenLink(token.getToken()));

		return Email.builder().eventId("creazione nuovo utente").from(applicationProperties.getSender())
				.templateName(EmailTemplates.CREATE_CREDENTIAL_TEMPLATE.getDescrizione()).templateModel(templateData)
				.subject("Attivazione credenziali").emailType(EmailType.HTML_TEMPLATE_MAIL).to(recipients).build();
	}

	public Email buildCredentialReminder(PasswordToken token, Utente utente, Integer ore) {
		List<String> recipients = new ArrayList<>();
		Map<String, Object> templateData = new HashMap<>();

		if (!NotNullValidator.validate(token))
			throw new NullFieldException(String.format("%s must not be null!", NotNullValidator.getDetails(token)));
		if (!NotNullValidator.validate(utente))
			throw new NullFieldException(String.format("%s must not be null!", NotNullValidator.getDetails(utente)));

		recipients.add(utente.getMailAziendale());

		templateData.put("username", utente.getUsername());
		templateData.put("ore", ore);

		templateData.put("link", createTokenLink(token.getToken()));

		return Email.builder().eventId("reminder attivazione credenziali").from(applicationProperties.getSender())
				.reminderDate(Utils.shifTimeByHour(token.getDataScadenza(), Utils.CREDENTIAL_REMINDER))
				.templateName(EmailTemplates.REMINDER_CREDENTIAL_TEMPLATE.getDescrizione()).templateModel(templateData)
				.subject("Attivazione credenziali").emailType(EmailType.HTML_TEMPLATE_MAIL).to(recipients).build();
	}

	public URI createTokenLink(String token) {
		try {
			return new URI(applicationProperties.getPasswordUpdateEndpoint() + "/" + token);
		} catch (Exception ex) {
			throw new URIException(ex.getMessage());
		}
	}
}
