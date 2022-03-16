package com.perigea.tracker.timesheet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.commons.dto.ContactDto;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;

@Service
public class ContactDetailsService {

	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private GruppoContattoService gruppoContattoService;
	
	/**
	 * lettura dei recapiti di un utente
	 * @param userId
	 * @return
	 */
	public ContactDto readUserContactDetails(String userId) {
		Utente utente = utenteService.readUtente(userId);
		ContactDto contactDetails = dtoEntityMapper.entityToContactDto(utente);
		return contactDetails;
	}
	
	/**
	 * lettura dei recapiti di tutti gli utenti di un determinato gruppo
	 * @param groupId
	 * @return
	 */
	public List<ContactDto> readAllContactDetails(Long groupId) {
		List<Utente> utenti = gruppoContattoService.readAllContactsByGroupId(groupId);
		List<ContactDto> details = dtoEntityMapper.entityToContactDtoList(utenti);
		return details;
	}
}
