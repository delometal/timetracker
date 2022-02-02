package com.perigea.tracker.timesheet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.dto.AnagraficaDto;
import com.perigea.tracker.timesheet.dto.ClienteDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.entity.Anagrafica;
import com.perigea.tracker.timesheet.entity.Cliente;
import com.perigea.tracker.timesheet.service.ClienteService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@RestController
@RequestMapping("/clienti")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@PostMapping(value = "/create")
	public ResponseEntity<GenericWrapperResponse<ClienteDto>> saveCliente(@RequestBody ClienteDto clienteDto) {
		Cliente cliente = dtoEntityMapper.dtoToEntity(clienteDto);
		cliente = clienteService.saveCliente(cliente);
		clienteDto = dtoEntityMapper.entityToDto(cliente);
		GenericWrapperResponse<ClienteDto> genericResponse = GenericWrapperResponse
				.<ClienteDto>builder().dataRichiesta(Utils.now()).risultato(clienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read")
	public ResponseEntity<GenericWrapperResponse<ClienteDto>> readCliente(@RequestParam String ragioneSociale) {
		Cliente cliente = clienteService.readCliente(ragioneSociale);
		ClienteDto clienteDto = dtoEntityMapper.entityToDto(cliente);
		GenericWrapperResponse<ClienteDto> genericResponse = GenericWrapperResponse
				.<ClienteDto>builder().dataRichiesta(Utils.now()).risultato(clienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<GenericWrapperResponse<ClienteDto>> updateCliente(@RequestBody ClienteDto anaClienteDto) {
		Cliente cliente = dtoEntityMapper.dtoToEntity(anaClienteDto);
		cliente = clienteService.saveCliente(cliente);
		ClienteDto clienteDto = dtoEntityMapper.entityToDto(cliente);
		GenericWrapperResponse<ClienteDto> genericResponse = GenericWrapperResponse
				.<ClienteDto>builder().dataRichiesta(Utils.now()).risultato(clienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<GenericWrapperResponse<ClienteDto>> deleteCliente(@RequestParam String id) {
		Cliente cliente = clienteService.readCliente(id);
		ClienteDto clienteDto = dtoEntityMapper.entityToDto(cliente);
		clienteService.deleteCliente(id);
		GenericWrapperResponse<ClienteDto> genericResponse = GenericWrapperResponse
				.<ClienteDto>builder().dataRichiesta(Utils.now()).risultato(clienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping(value = "/{id}/contatti/create-contatto")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDto>> createContatto(@PathVariable(name = "id") String id, @RequestBody AnagraficaDto anagraficaDto) {
		Cliente cliente = clienteService.readCliente(id);
		Anagrafica contatto = dtoEntityMapper.dtoToEntity(anagraficaDto);
		clienteService.addContatto(cliente, contatto);
		AnagraficaDto dto = dtoEntityMapper.entityToDto(contatto);
		GenericWrapperResponse<AnagraficaDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDto>builder().dataRichiesta(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping(value = "/{id}/contatti/remove-contatto")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDto>> removeContatto(@PathVariable(name = "id") String id, @RequestBody AnagraficaDto anagraficaDto) {
		Cliente cliente = clienteService.readCliente(id);
		Anagrafica contatto = dtoEntityMapper.dtoToEntity(anagraficaDto);
		clienteService.removeContatto(cliente, contatto);
		AnagraficaDto dto = dtoEntityMapper.entityToDto(contatto);
		GenericWrapperResponse<AnagraficaDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDto>builder().dataRichiesta(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@DeleteMapping(value = "/{id}/contatti/delete-contatto")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDto>> deleteContatto(@PathVariable(name = "id") String id, @RequestBody AnagraficaDto anagraficaDto) {
		Cliente cliente = clienteService.readCliente(id);
		Anagrafica contatto = dtoEntityMapper.dtoToEntity(anagraficaDto);
		clienteService.deleteContatto(cliente, contatto);
		AnagraficaDto dto = dtoEntityMapper.entityToDto(contatto);
		GenericWrapperResponse<AnagraficaDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDto>builder().dataRichiesta(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
}
