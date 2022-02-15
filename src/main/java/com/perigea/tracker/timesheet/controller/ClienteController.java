package com.perigea.tracker.timesheet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.commons.dto.ClienteDto;
import com.perigea.tracker.commons.dto.GenericWrapperResponse;
import com.perigea.tracker.commons.dto.UtenteDto;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.Cliente;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.ClienteService;

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
				.<ClienteDto>builder().timestamp(Utils.now()).risultato(clienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-all")
	public ResponseEntity<GenericWrapperResponse<List<ClienteDto>>> readAll() {
		List<Cliente> clienti = clienteService.readAll();
		List<ClienteDto> clientiDto = dtoEntityMapper.entityToDtoClienteList(clienti);
		GenericWrapperResponse<List<ClienteDto>> genericResponse = GenericWrapperResponse
				.<List<ClienteDto>>builder().timestamp(Utils.now()).risultato(clientiDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-by-id/{id}")
	public ResponseEntity<GenericWrapperResponse<ClienteDto>> readClienteById(@PathVariable(name = "id") String id) {
		Cliente cliente = clienteService.readClienteById(id);
		ClienteDto clienteDto = dtoEntityMapper.entityToDto(cliente);
		GenericWrapperResponse<ClienteDto> genericResponse = GenericWrapperResponse
				.<ClienteDto>builder().timestamp(Utils.now()).risultato(clienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-by-partita-iva/{partitaIva}")
	public ResponseEntity<GenericWrapperResponse<ClienteDto>> readCliente(@PathVariable(name = "partitaIva") String partitaIva) {
		Cliente cliente = clienteService.readClienteByPartitaIva(partitaIva);
		ClienteDto clienteDto = dtoEntityMapper.entityToDto(cliente);
		GenericWrapperResponse<ClienteDto> genericResponse = GenericWrapperResponse
				.<ClienteDto>builder().timestamp(Utils.now()).risultato(clienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<GenericWrapperResponse<ClienteDto>> updateCliente(@RequestBody ClienteDto clienteDto) {
		Cliente cliente = dtoEntityMapper.dtoToEntity(clienteDto);
		cliente = clienteService.saveCliente(cliente);
		clienteDto = dtoEntityMapper.entityToDto(cliente);
		GenericWrapperResponse<ClienteDto> genericResponse = GenericWrapperResponse
				.<ClienteDto>builder().timestamp(Utils.now()).risultato(clienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete-by-id/{id}")
	public ResponseEntity<GenericWrapperResponse<ClienteDto>> deleteClienteById(@PathVariable(name = "id") String id) {
		Cliente cliente = clienteService.readClienteById(id);
		ClienteDto clienteDto = dtoEntityMapper.entityToDto(cliente);
		clienteService.deleteClienteById(id);
		GenericWrapperResponse<ClienteDto> genericResponse = GenericWrapperResponse
				.<ClienteDto>builder().timestamp(Utils.now()).risultato(clienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@DeleteMapping(value = "/delete-by-partita-iva/{partitaIva}")
	public ResponseEntity<GenericWrapperResponse<ClienteDto>> deleteClienteByPartitaIva(@PathVariable(name = "partitaIva") String partitaIva) {
		Cliente cliente = clienteService.readClienteByPartitaIva(partitaIva);
		ClienteDto clienteDto = dtoEntityMapper.entityToDto(cliente);
		clienteService.deleteClienteById(cliente.getCodiceAzienda());
		GenericWrapperResponse<ClienteDto> genericResponse = GenericWrapperResponse
				.<ClienteDto>builder().timestamp(Utils.now()).risultato(clienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping(value = "/{id}/contatti/create-contatto")
	public ResponseEntity<GenericWrapperResponse<UtenteDto>> createContatto(@PathVariable(name = "id") String id, @RequestBody UtenteDto contattoDto) {
		Cliente cliente = clienteService.readClienteById(id);
		Utente contatto = clienteService.findContatto(contattoDto.getCodicePersona());
		clienteService.addContatto(cliente, contatto);
		UtenteDto dto = dtoEntityMapper.entityToDto(contatto);
		GenericWrapperResponse<UtenteDto> genericResponse = GenericWrapperResponse
				.<UtenteDto>builder().timestamp(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping(value = "/{id}/contatti/remove-contatto")
	public ResponseEntity<GenericWrapperResponse<UtenteDto>> removeContatto(@PathVariable(name = "id") String id, @RequestBody UtenteDto contattoDto) {
		Cliente cliente = clienteService.readClienteById(id);
		Utente contatto = clienteService.findContatto(contattoDto.getCodicePersona());
		clienteService.removeContatto(cliente, contatto);
		UtenteDto dto = dtoEntityMapper.entityToDto(contatto);
		GenericWrapperResponse<UtenteDto> genericResponse = GenericWrapperResponse
				.<UtenteDto>builder().timestamp(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@DeleteMapping(value = "/{id}/contatti/delete-contatto")
	public ResponseEntity<GenericWrapperResponse<UtenteDto>> deleteContatto(@PathVariable(name = "id") String id, @RequestBody UtenteDto contattoDto) {
		Cliente cliente = clienteService.readClienteById(id);
		Utente contatto = clienteService.findContatto(contattoDto.getCodicePersona());
		clienteService.deleteContatto(cliente, contatto);
		UtenteDto dto = dtoEntityMapper.entityToDto(contatto);
		GenericWrapperResponse<UtenteDto> genericResponse = GenericWrapperResponse
				.<UtenteDto>builder().timestamp(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
}
