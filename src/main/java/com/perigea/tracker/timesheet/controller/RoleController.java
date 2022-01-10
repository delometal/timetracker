package com.perigea.tracker.timesheet.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.RuoliDto;
import com.perigea.tracker.timesheet.service.RuoloService;


@RestController
public class RoleController {
	
	@Autowired
	private RuoloService roleService;
	

	// Metodo per creare un ruolo
	@PostMapping(value = "/create-role")
	public ResponseEntity <GenericWrapperResponse<RuoliDto>> createRole(@RequestBody RuoliDto dtoParam,  @RequestParam String nomeModifica, @RequestParam String cognomeModifica) {
		RuoliDto dto= roleService.createRole(dtoParam);
		Date date=new Date();
		GenericWrapperResponse<RuoliDto>genericDto=GenericWrapperResponse.<RuoliDto>builder()
				.dataRichiesta(date)
				.utenteModifica(nomeModifica+cognomeModifica)
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	// Metodo per leggere un ruolo
	@GetMapping(value = "/read-role")
	public ResponseEntity <GenericWrapperResponse<RuoliDto>> readRole(@RequestParam String roleName,@RequestParam String nomeModifica, @RequestParam String cognomeModifica) {
		RuoliDto dto=roleService.readRole(roleName);
		Date date=new Date();
		GenericWrapperResponse<RuoliDto>genericDto=GenericWrapperResponse.<RuoliDto>builder()
				.dataRichiesta(date)
				.utenteModifica(nomeModifica+cognomeModifica)
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	// Metodo per cancellare un ruolo
	@GetMapping(value = "/delete-role")
	public ResponseEntity <?> deleteRole(@RequestParam String roleName) {
		roleService.deleteRole(roleName);
		return ResponseEntity.ok("Cancellazione ruolo effettuata");
	}

	// Metodo per aggiornare un ruolo
	@PostMapping(value = "/update-role")
	public ResponseEntity <?> updateRole(@RequestBody RuoliDto dtoParam,@RequestParam String nomeModifica, @RequestParam String cognomeModifica) {
		RuoliDto dto=roleService.updateRole(dtoParam);
		Date date=new Date();
		GenericWrapperResponse<RuoliDto>genericDto=GenericWrapperResponse.<RuoliDto>builder()
				.dataRichiesta(date)
				.utenteModifica(nomeModifica+cognomeModifica)
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

}
