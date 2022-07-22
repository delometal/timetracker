package com.perigea.tracker.timesheet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.perigea.tracker.commons.dto.ClienteDto;
import com.perigea.tracker.commons.dto.LogoAziendaDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.timesheet.entity.Cliente;
import com.perigea.tracker.timesheet.entity.LogoAzienda;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.ClienteService;
import com.perigea.tracker.timesheet.service.FileService;

@RestController
@RequestMapping("/clienti")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private FileService fileService;

	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@PostMapping(value = "/create")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_SALES')")
	public ResponseEntity<ResponseDto<ClienteDto>> saveCliente(@RequestBody ClienteDto clienteDto) {
		Cliente cliente = dtoEntityMapper.dtoToEntity(clienteDto);
		cliente = clienteService.saveCliente(cliente);
		clienteDto = dtoEntityMapper.entityToDto(cliente);
		ResponseDto<ClienteDto> genericResponse = ResponseDto.<ClienteDto>builder().data(clienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-all")
	public ResponseEntity<ResponseDto<List<ClienteDto>>> readAll() {
		List<Cliente> clienti = clienteService.readAll();
		List<ClienteDto> clientiDto = dtoEntityMapper.entityToDtoClienteList(clienti);
		ResponseDto<List<ClienteDto>> genericResponse = ResponseDto.<List<ClienteDto>>builder().data(clientiDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-by-id/{codiceAzienda}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_SALES')")
	public ResponseEntity<ResponseDto<ClienteDto>> readClienteById(@PathVariable(name = "codiceAzienda") String codiceAzienda) {
		Cliente cliente = clienteService.readClienteById(codiceAzienda);
		ClienteDto clienteDto = dtoEntityMapper.entityToDto(cliente);
		ResponseDto<ClienteDto> genericResponse = ResponseDto.<ClienteDto>builder().data(clienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-by-partita-iva/{partitaIva}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_SALES')")
	public ResponseEntity<ResponseDto<ClienteDto>> readCliente(@PathVariable(name = "partitaIva") String partitaIva) {
		Cliente cliente = clienteService.readClienteByPartitaIva(partitaIva);
		ClienteDto clienteDto = dtoEntityMapper.entityToDto(cliente);
		ResponseDto<ClienteDto> genericResponse = ResponseDto.<ClienteDto>builder().data(clienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_SALES')")
	public ResponseEntity<ResponseDto<ClienteDto>> updateCliente(@RequestBody ClienteDto clienteDto) {
		Cliente cliente = dtoEntityMapper.dtoToEntity(clienteDto);
		cliente = clienteService.saveCliente(cliente);
		clienteDto = dtoEntityMapper.entityToDto(cliente);
		ResponseDto<ClienteDto> genericResponse = ResponseDto.<ClienteDto>builder().data(clienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete-by-id/{codiceAzienda}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_SALES')")
	public ResponseEntity<ResponseDto<ClienteDto>> deleteClienteById(@PathVariable(name = "codiceAzienda") String codiceAzienda) {
		Cliente cliente = clienteService.readClienteById(codiceAzienda);
		ClienteDto clienteDto = dtoEntityMapper.entityToDto(cliente);
		clienteService.deleteClienteById(codiceAzienda);
		ResponseDto<ClienteDto> genericResponse = ResponseDto.<ClienteDto>builder().data(clienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@DeleteMapping(value = "/delete-by-partita-iva/{partitaIva}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_SALES')")
	public ResponseEntity<ResponseDto<ClienteDto>> deleteClienteByPartitaIva(@PathVariable(name = "partitaIva") String partitaIva) {
		Cliente cliente = clienteService.readClienteByPartitaIva(partitaIva);
		ClienteDto clienteDto = dtoEntityMapper.entityToDto(cliente);
		clienteService.deleteClienteById(cliente.getCodiceAzienda());
		ResponseDto<ClienteDto> genericResponse = ResponseDto.<ClienteDto>builder().data(clienteDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping("upload-logo/{codiceAzienda}")
	public ResponseEntity<ResponseDto<String>> uploadLogo(@PathVariable(name = "codiceAzienda") String codiceAzienda, @RequestParam("file") MultipartFile file) {
		try {
			fileService.uploadLogoCliente(codiceAzienda, file);
			ResponseDto<String> genericResponse = ResponseDto.<String>builder()
					.data("Uploaded the file successfully: " + file.getOriginalFilename()).build();
			return ResponseEntity.ok(genericResponse);
		} catch (Exception e) {
			ResponseDto<String> genericResponse = ResponseDto.<String>builder()
					.data(e.getMessage()).code(HttpStatus.BAD_REQUEST.value()).build();
			return ResponseEntity.badRequest().body(genericResponse);
		}
	}
	
	@ResponseBody
	@GetMapping(value="/archivio/{codiceAzienda}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<?> downLoadLogo(@PathVariable String codiceAzienda) {
		LogoAzienda data = fileService.getLogoCliente(codiceAzienda);
		Resource resource = new ByteArrayResource(data.getLogo());
		String filename = data.getFilename();
		return ResponseEntity.ok()
		        .contentType(MediaType.APPLICATION_OCTET_STREAM)
		        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ filename + "\"")
		        .body(resource);
	}
	
	@GetMapping(value = "/read-logo/{codiceAzienda}")
	public ResponseEntity<ResponseDto<LogoAziendaDto>> readProfileImage(@PathVariable String codiceAzienda) {
		LogoAzienda logo = fileService.getLogoCliente(codiceAzienda);
		LogoAziendaDto logoDto = dtoEntityMapper.entityToDto(logo);
		ResponseDto<LogoAziendaDto> genericResponse = ResponseDto.<LogoAziendaDto>builder().data(logoDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	

}
