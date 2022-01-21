package com.perigea.tracker.timesheet.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.AnagraficaClienteDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.entity.AnagraficaCliente;
import com.perigea.tracker.timesheet.service.ClienteService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@RestController
@RequestMapping("/clienti")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PostMapping(value = "/create-anagrafica-cliente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaClienteDto>> createClient(@RequestBody AnagraficaClienteDto dtoParam) {
		AnagraficaCliente anagraficaCliente = clienteService.createAnagraficaCliente(dtoParam);
		AnagraficaClienteDto anagraficaClienteDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(anagraficaCliente);
		GenericWrapperResponse<AnagraficaClienteDto> genericResponse = GenericWrapperResponse
				.<AnagraficaClienteDto>builder().dataRichiesta(new Date()).risultato(anagraficaClienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-anagrafica-cliente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaClienteDto>> readClient(@RequestParam String ragioneSociale) {
		AnagraficaCliente anagraficaCliente = clienteService.readAnagraficaCliente(ragioneSociale);
		AnagraficaClienteDto anagraficaClienteDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(anagraficaCliente);
		GenericWrapperResponse<AnagraficaClienteDto> genericResponse = GenericWrapperResponse
				.<AnagraficaClienteDto>builder().dataRichiesta(new Date()).risultato(anagraficaClienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update-anagrafica-cliente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaClienteDto>> updateClient(@RequestBody AnagraficaClienteDto dtoParam) {
		AnagraficaCliente anagraficaCliente = clienteService.updateAnagraficaCliente(dtoParam);
		AnagraficaClienteDto anagraficaClienteDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(anagraficaCliente);
		GenericWrapperResponse<AnagraficaClienteDto> genericResponse = GenericWrapperResponse
				.<AnagraficaClienteDto>builder().dataRichiesta(new Date()).risultato(anagraficaClienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete-anagrafica-cliente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaClienteDto>> deleteClient(@RequestParam String ragioneSociale) {
		AnagraficaCliente anagraficaCliente = clienteService.deleteAnagraficaCliente(ragioneSociale);
		AnagraficaClienteDto anagraficaClienteDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(anagraficaCliente);
		GenericWrapperResponse<AnagraficaClienteDto> genericResponse = GenericWrapperResponse
				.<AnagraficaClienteDto>builder().dataRichiesta(new Date()).risultato(anagraficaClienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
}
