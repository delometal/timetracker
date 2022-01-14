//package com.perigea.tracker.timesheet.controller;
//
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
//import com.perigea.tracker.timesheet.dto.UtentePostDto;
//import com.perigea.tracker.timesheet.dto.UtenteViewDto;
//import com.perigea.tracker.timesheet.service.UtenteService;
//
//
//
//
//
////@ TODO mapstruct controllare cosa fa per gestire il get e le entity ( come metterlo nel pom)
////@ FIXME CAPIRE SE SERVE SOLO UN CONTROLLER UTENTI/DIPENDENTI
//
//@RestController
//public class UtenteController {
//
//	@Autowired
//	private UtenteService userService;
//
//	// Metodo per creare un utente
//	@PostMapping(value = "/create-user")
//	public ResponseEntity<GenericWrapperResponse<UtenteViewDto>> createUser(@RequestBody UtentePostDto dtoParam, @RequestParam String codiceResponsabile) {
//		UtenteViewDto dto = userService.createUtente(dtoParam, codiceResponsabile);
//		GenericWrapperResponse<UtenteViewDto> genericResponse = GenericWrapperResponse.<UtenteViewDto>builder()
//				.dataRichiesta(new Date())
//				.risultato(dto)
//				.build();
//		return ResponseEntity.ok(genericResponse);
//	}
//
//	// Metodo per leggere un utente
//	@GetMapping(value = "/read-user")
//	public ResponseEntity<GenericWrapperResponse<UtenteViewDto>> readUser(@RequestParam String codicePersona) {
//		UtenteViewDto dto = userService.readUtente(codicePersona);
//		GenericWrapperResponse<UtenteViewDto> genericResponse = GenericWrapperResponse.<UtenteViewDto>builder()
//				.dataRichiesta(new Date())
//				.risultato(dto)
//				.build();
//		return ResponseEntity.ok(genericResponse);
//	}
//
//	// Metodo per cancellare un utente
//	@GetMapping(value = "/delete-user")
//	public ResponseEntity<GenericWrapperResponse<UtenteViewDto>> deleteUser(@RequestParam String codicePersona) {
//		UtenteViewDto dto = userService.deleteUtente(codicePersona);
//		GenericWrapperResponse<UtenteViewDto> genericDto = GenericWrapperResponse.<UtenteViewDto>builder()
//				.dataRichiesta(new Date())
//				.risultato(dto)
//				.build();
//		return ResponseEntity.ok(genericDto);
//	}
//
//	// Metodo per aggiornare un utente
//	@PostMapping(value = "/update-user")
//	public ResponseEntity<GenericWrapperResponse<UtenteViewDto>> updateUser(@RequestBody UtentePostDto utenteDto, @RequestParam String codiceResponsabile) {
//		UtenteViewDto dto = userService.updateUtente(utenteDto, codiceResponsabile);
//		GenericWrapperResponse<UtenteViewDto> genericResponse = GenericWrapperResponse.<UtenteViewDto>builder()
//				.dataRichiesta(new Date())
//				.risultato(dto)
//				.build();
//		return ResponseEntity.ok(genericResponse);
//	}
//
//	// Metodo per aggiornare lo status di un utente
//	@PostMapping(value = "/edit-status-user")
//	public ResponseEntity<GenericWrapperResponse<UtenteViewDto>> editStatusUser(@RequestBody UtentePostDto utenteDto) {
//		UtenteViewDto dto = userService.editStatusUser(utenteDto);
//		GenericWrapperResponse<UtenteViewDto> genericResponse = GenericWrapperResponse.<UtenteViewDto>builder()
//				.dataRichiesta(new Date())
//				.risultato(dto)
//				.build();
//		return ResponseEntity.ok(genericResponse);
//	}
//
//}
