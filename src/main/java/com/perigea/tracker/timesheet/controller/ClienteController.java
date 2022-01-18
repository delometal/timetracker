package com.perigea.tracker.timesheet.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.AnagraficaClienteDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.entity.AnagraficaCliente;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.ClienteService;



@RestController
@RequestMapping("/clienti")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PostMapping(value = "/create-anagrafica-cliente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaClienteDto>> createClient(@RequestBody AnagraficaClienteDto dtoParam) {
//		AnagraficaClienteDto dto = clienteService.createCustomerPersonalData(dtoParam);
		AnagraficaCliente anagraficaCliente = clienteService.createCustomerPersonalData(dtoParam);
		AnagraficaClienteDto anagraficaClienteDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(anagraficaCliente);
		GenericWrapperResponse<AnagraficaClienteDto> genericResponse = GenericWrapperResponse
				.<AnagraficaClienteDto>builder().dataRichiesta(new Date()).risultato(anagraficaClienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-anagrafica-cliente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaClienteDto>> readClient(@RequestParam String ragioneSociale) {
//		AnagraficaClienteDto dto = clienteService.readCustomerPersonalData(ragioneSociale);
		AnagraficaCliente anagraficaCliente = clienteService.readCustomerPersonalData(ragioneSociale);
		AnagraficaClienteDto anagraficaClienteDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(anagraficaCliente);
		GenericWrapperResponse<AnagraficaClienteDto> genericResponse = GenericWrapperResponse
				.<AnagraficaClienteDto>builder().dataRichiesta(new Date()).risultato(anagraficaClienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PostMapping(value = "/update-anagrafica-cliente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaClienteDto>> updateClient(@RequestBody AnagraficaClienteDto dtoParam) {
//		AnagraficaClienteDto dto = clienteService.updateCustomerPersonalData(dtoParam);
		AnagraficaCliente anagraficaCliente = clienteService.updateCustomerPersonalData(dtoParam);
		AnagraficaClienteDto anagraficaClienteDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(anagraficaCliente);
		GenericWrapperResponse<AnagraficaClienteDto> genericResponse = GenericWrapperResponse
				.<AnagraficaClienteDto>builder().dataRichiesta(new Date()).risultato(anagraficaClienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/delete-anagrafica-cliente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaClienteDto>> deleteClient(@RequestParam String ragioneSociale) {
//		AnagraficaClienteDto dto = clienteService.deleteCustomerPersonalData(ragioneSociale);
		AnagraficaCliente anagraficaCliente = clienteService.deleteCustomerPersonalData(ragioneSociale);
		AnagraficaClienteDto anagraficaClienteDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(anagraficaCliente);
		GenericWrapperResponse<AnagraficaClienteDto> genericResponse = GenericWrapperResponse
				.<AnagraficaClienteDto>builder().dataRichiesta(new Date()).risultato(anagraficaClienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
}
