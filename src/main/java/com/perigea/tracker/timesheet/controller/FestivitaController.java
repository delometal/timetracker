package com.perigea.tracker.timesheet.controller;

import java.util.Date;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.FestivitaDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.service.FestivitaService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@RestController
@RequestMapping("/backoffice/festivita")
public class FestivitaController {

	@Autowired
	private FestivitaService festeService;

	@PostMapping(value = "/create-festivita")
	public ResponseEntity<GenericWrapperResponse<FestivitaDto>> createFestivita(@RequestBody FestivitaDto festivitaDto) {
		Festivita festivita = festeService.createFestivita(festivitaDto);
		FestivitaDto dtoFestivita = DtoEntityMapper.INSTANCE.FromEntityToDtoFestivita(festivita);
		GenericWrapperResponse<FestivitaDto> genericDto = GenericWrapperResponse.<FestivitaDto>builder()
				.dataRichiesta(new Date()).risultato(dtoFestivita).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/read-festivita/{id}")
	public ResponseEntity<GenericWrapperResponse<FestivitaDto>> readFestivita(@PathParam("id") Integer id) {
		Festivita festivita = festeService.readFestivita(id);
		FestivitaDto dtoFestivita = DtoEntityMapper.INSTANCE.FromEntityToDtoFestivita(festivita);
		GenericWrapperResponse<FestivitaDto> genericDto = GenericWrapperResponse.<FestivitaDto>builder()
				.dataRichiesta(new Date()).risultato(dtoFestivita).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@PutMapping(value = "/update-festivita")
	public ResponseEntity<GenericWrapperResponse<FestivitaDto>> updateFestivita(@RequestBody FestivitaDto festivitaDto) {
		Festivita festivita = festeService.updateFestivita(festivitaDto);
		FestivitaDto dtoFestivita = DtoEntityMapper.INSTANCE.FromEntityToDtoFestivita(festivita);
		GenericWrapperResponse<FestivitaDto> genericDto = GenericWrapperResponse.<FestivitaDto>builder()
				.dataRichiesta(new Date()).risultato(dtoFestivita).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@DeleteMapping(value = "/delete-festivita")
	public ResponseEntity<GenericWrapperResponse<FestivitaDto>> deleteFestivita(@PathParam("id") Integer id) {
		Festivita festivita = festeService.deleteFestivita(id);
		FestivitaDto dtoFestivita = DtoEntityMapper.INSTANCE.FromEntityToDtoFestivita(festivita);
		GenericWrapperResponse<FestivitaDto> genericDto = GenericWrapperResponse.<FestivitaDto>builder()
				.dataRichiesta(new Date()).risultato(dtoFestivita).build();
		return ResponseEntity.ok(genericDto);
	}
	
}
