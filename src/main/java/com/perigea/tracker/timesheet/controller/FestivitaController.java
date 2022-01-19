package com.perigea.tracker.timesheet.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
//		FestivitaDto dto = festeService.createFestivita(festivitaDto);
		Festivita festivita = festeService.createFestivita(festivitaDto);
		FestivitaDto dtoFestivita = DtoEntityMapper.INSTANCE.FromEntityToDtoFestivita(festivita);
		GenericWrapperResponse<FestivitaDto> genericDto = GenericWrapperResponse.<FestivitaDto>builder()
				.dataRichiesta(new Date()).risultato(dtoFestivita).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@PostMapping(value = "/update-festivita")
	public ResponseEntity<GenericWrapperResponse<FestivitaDto>> updateFestivita(@RequestBody FestivitaDto festivitaDto) {
//		FestivitaDto dto = festeService.updateFestivita(festivitaDto);
		Festivita festivita = festeService.updateFestivita(festivitaDto);
		FestivitaDto dtoFestivita = DtoEntityMapper.INSTANCE.FromEntityToDtoFestivita(festivita);
		GenericWrapperResponse<FestivitaDto> genericDto = GenericWrapperResponse.<FestivitaDto>builder()
				.dataRichiesta(new Date()).risultato(dtoFestivita).build();
		return ResponseEntity.ok(genericDto);
	}
}
