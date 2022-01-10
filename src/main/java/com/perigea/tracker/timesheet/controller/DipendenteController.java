package com.perigea.tracker.timesheet.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.service.DipendenteService;
import com.perigea.tracker.timesheet.wrapper.AnagraficaDipendenteWrapper;


@RestController
public class DipendenteController {
	
	@Autowired
	private DipendenteService dipendenteService;
	
	@PostMapping(value = "/create-dipendente")
	public ResponseEntity <GenericWrapperResponse<AnagraficaDipendenteDto>> createUser(@RequestBody AnagraficaDipendenteWrapper wrapper, @RequestParam String nomeModifica, @RequestParam String cognomeModifica) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		AnagraficaDipendenteDto dto=dipendenteService.createDipendente(wrapper.getDipendenteDto(),wrapper.getUtenteDto(),wrapper.getCodiceResponsabile());
		Date date=new Date();
		GenericWrapperResponse<AnagraficaDipendenteDto> genericDto=GenericWrapperResponse.<AnagraficaDipendenteDto>builder()
				.dataRichiesta(date)
				.utenteModifica(nomeModifica+cognomeModifica)
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericDto);
	}
	
	// Metodo per leggere un utente
	@GetMapping(value = "/read-dipendente")
	public ResponseEntity <GenericWrapperResponse<AnagraficaDipendenteDto>> readUser(@RequestParam String codicePersona,@RequestParam String nomeModifica, @RequestParam String cognomeModifica) {
		AnagraficaDipendenteDto dto=dipendenteService.readDipendente(codicePersona);
		Date date=new Date();
		GenericWrapperResponse<AnagraficaDipendenteDto> genericDto=GenericWrapperResponse.<AnagraficaDipendenteDto>builder()
				.dataRichiesta(date)
				.utenteModifica(nomeModifica+cognomeModifica)
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	// Metodo per cancellare un utente
	@GetMapping(value = "/delete-dipendente")
	public ResponseEntity <GenericWrapperResponse<String>> deleteUser(@RequestParam String codicePersona,@RequestParam String nomeModifica, @RequestParam String cognomeModifica) {
		dipendenteService.deleteDipendente(codicePersona);
		Date date=new Date();
		GenericWrapperResponse<String>genericDto=GenericWrapperResponse.<String>builder()
				.dataRichiesta(date)
				.utenteModifica(nomeModifica+cognomeModifica)
				.risultato("Dipendente eliminato")
				.build();
		return ResponseEntity.ok(genericDto);
	}

	// Metodo per aggiornare un utente
	@PostMapping(value = "/update-dipendente")
	public ResponseEntity <GenericWrapperResponse<AnagraficaDipendenteDto>> updateUser(@RequestBody AnagraficaDipendenteDto dtoParam,@RequestParam String nomeModifica, @RequestParam String cognomeModifica) {
		AnagraficaDipendenteDto dto=dipendenteService.updateDipendente(dtoParam);
		Date date=new Date();
		GenericWrapperResponse<AnagraficaDipendenteDto> genericDto=GenericWrapperResponse.<AnagraficaDipendenteDto>builder()
				.dataRichiesta(date)
				.utenteModifica(nomeModifica+cognomeModifica)
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

}
