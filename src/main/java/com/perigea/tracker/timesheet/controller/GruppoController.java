package com.perigea.tracker.timesheet.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.perigea.tracker.commons.dto.ContactDto;
import com.perigea.tracker.commons.dto.GruppoContattoDto;
import com.perigea.tracker.commons.dto.MeetingEventDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.Gruppo;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.kafka.sender.KafkaSender;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.rest.NotificationRestClient;
import com.perigea.tracker.timesheet.service.ContactDetailsService;
import com.perigea.tracker.timesheet.service.GruppoContattoService;
import com.perigea.tracker.timesheet.service.UtenteService;

@RestController
@RequestMapping("/gruppi")
public class GruppoController {

	// TODO ricerca per filtri (nome/username/...) 

	@Autowired
	private Logger logger;
	
	@Autowired
	private KafkaSender kafkaSender;
	
	@Autowired
	private GruppoContattoService gruppoContattoService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private ContactDetailsService contactDetailsService;
	
	@Autowired
	private NotificationRestClient restClient;
	
	private static final String CREATE_MEETING_ENDPOINT = "meeting/create-meeting";
	
	

	@PostMapping(value = "/create")
	public ResponseEntity<ResponseDto<GruppoContattoDto>> createGruppo(@RequestBody GruppoContattoDto gruppoContattoDto) {
		Gruppo gruppo = dtoEntityMapper.dtoToEntity(gruppoContattoDto);
		List<Utente> contatti= gruppoContattoDto.getContatti().stream()
				.map(c -> utenteService.loadUtente(c.getCodicePersona()))
				.collect(Collectors.toList());
		gruppo.setContatti(contatti);
		gruppo = gruppoContattoService.createGruppo(gruppo);
		gruppoContattoDto.setId(gruppo.getId());
		ResponseDto<GruppoContattoDto> genericResponse = ResponseDto.<GruppoContattoDto>builder().data(gruppoContattoDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read/{id}")
	public ResponseEntity<ResponseDto<GruppoContattoDto>> readGruppo(@PathVariable(name="id") Long id) {
		Gruppo gruppoContatto = gruppoContattoService.readGruppo(id);
		GruppoContattoDto dto = dtoEntityMapper.entityToDto(gruppoContatto);
		ResponseDto<GruppoContattoDto> genericResponse = ResponseDto.<GruppoContattoDto>builder().data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<ResponseDto<GruppoContattoDto>> updateGruppo(@RequestBody GruppoContattoDto gruppoContattoDto) {
		gruppoContattoService.deleteGruppo(gruppoContattoDto.getId());
		Gruppo gruppo = dtoEntityMapper.dtoToEntity(gruppoContattoDto);
		List<Utente> contatti= gruppoContattoDto.getContatti().stream()
				.map(c -> utenteService.loadUtente(c.getCodicePersona()))
				.collect(Collectors.toList());
		gruppo.setContatti(contatti);
		gruppo = gruppoContattoService.createGruppo(gruppo);
		gruppoContattoDto.setId(gruppo.getId());
		ResponseDto<GruppoContattoDto> genericResponse = ResponseDto.<GruppoContattoDto>builder().data(gruppoContattoDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<ResponseDto<GruppoContattoDto>> deleteGruppo(@PathVariable(name="id") Long id) {
		Gruppo gruppoContatto = gruppoContattoService.readGruppo(id);
		GruppoContattoDto dto = dtoEntityMapper.entityToDto(gruppoContatto);
		gruppoContattoService.deleteGruppo(id);
		ResponseDto<GruppoContattoDto> genericResponse = ResponseDto.<GruppoContattoDto>builder().data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping(value = "/create-meeting-by-group/{groupId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ResponseDto<MeetingEventDto>> createMeeting(@PathVariable Long groupId, @RequestPart("event") MeetingEventDto event,
			@RequestParam("files") MultipartFile[] files) {
		List<ContactDto> contatti = contactDetailsService.readAllContactDetails(groupId);
		event.setParticipants(contatti);
		event.setAttachments(Utils.getFileList(files));
		if (kafkaSender.isBrokerOn()) {
			kafkaSender.send(event); 
		} else {
			logger.warn("Unable to send {}. Falling back to REST", event);
			//restClient.sendNotifica(event, CREATE_MEETING_ENDPOINT);
		}

		ResponseDto<MeetingEventDto> genericDto = ResponseDto.<MeetingEventDto>builder()
				.data(event)
				.timestamp(LocalDateTime.now())
				.build();
		return ResponseEntity.ok(genericDto);
	}
	
	
	
}
