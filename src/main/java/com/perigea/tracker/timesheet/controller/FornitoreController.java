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

import com.perigea.tracker.commons.dto.FornitoreDto;
import com.perigea.tracker.commons.dto.LogoAziendaDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.timesheet.entity.Fornitore;
import com.perigea.tracker.timesheet.entity.LogoAzienda;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.FileService;
import com.perigea.tracker.timesheet.service.FornitoreService;

@RestController
@RequestMapping("/fornitori")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class FornitoreController {

	@Autowired
	private FornitoreService fornitoreService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;
	
	@Autowired
	private FileService fileService;

	@PostMapping(value = "/create")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_SALES')")
	public ResponseEntity<ResponseDto<FornitoreDto>> saveFornitore(@RequestBody FornitoreDto fornitoreDto) {
		Fornitore fornitore = dtoEntityMapper.dtoToEntity(fornitoreDto);
		fornitore = fornitoreService.saveFornitore(fornitore);
		fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		ResponseDto<FornitoreDto> genericResponse = ResponseDto.<FornitoreDto>builder().data(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@GetMapping(value = "/read-all")
	public ResponseEntity<ResponseDto<List<FornitoreDto>>> readAll() {
		List<Fornitore> fornitori = fornitoreService.readAll();
		List<FornitoreDto> clientiDto = dtoEntityMapper.entityToDtoFornitoreList(fornitori);
		ResponseDto<List<FornitoreDto>> genericResponse = ResponseDto.<List<FornitoreDto>>builder().data(clientiDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-by-partita-iva/{partitaIva}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_SALES')")
	public ResponseEntity<ResponseDto<FornitoreDto>> readFornitoreByPartitaIva(@PathVariable(name = "partitaIva") String partitaIva) {
		Fornitore fornitore = fornitoreService.readFornitoreByPartitaIva(partitaIva);
		FornitoreDto fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		ResponseDto<FornitoreDto> genericResponse = ResponseDto.<FornitoreDto>builder().data(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-by-id/{codiceAzienda}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_SALES')")
	public ResponseEntity<ResponseDto<FornitoreDto>> readFornitoreById(@PathVariable(name = "codiceAzienda") String codiceAzienda) {
		Fornitore fornitore = fornitoreService.readFornitoreById(codiceAzienda);
		FornitoreDto fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		ResponseDto<FornitoreDto> genericResponse = ResponseDto.<FornitoreDto>builder().data(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_SALES')")
	public ResponseEntity<ResponseDto<FornitoreDto>> updateFornitore(@RequestBody FornitoreDto fornitoreDto) {
		Fornitore fornitore = dtoEntityMapper.dtoToEntity(fornitoreDto);
		fornitore = fornitoreService.saveFornitore(fornitore);
		fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		ResponseDto<FornitoreDto> genericResponse = ResponseDto.<FornitoreDto>builder().data(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete-by-partita-iva/{partitaIva}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_SALES')")
	public ResponseEntity<ResponseDto<FornitoreDto>> deleteFornitoreByPartitaIva(@PathVariable(name = "partitaIva") String partitaIva) {
		Fornitore fornitore = fornitoreService.readFornitoreByPartitaIva(partitaIva);
		FornitoreDto fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		fornitoreService.deleteFornitoreById(fornitore.getCodiceAzienda());
		ResponseDto<FornitoreDto> genericResponse = ResponseDto.<FornitoreDto>builder().data(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete-by-id/{codiceAzienda}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_SALES')")
	public ResponseEntity<ResponseDto<FornitoreDto>> deleteFornitoreById(@PathVariable(name = "codiceAzienda") String codiceAzienda) {
		Fornitore fornitore = fornitoreService.readFornitoreById(codiceAzienda);
		FornitoreDto fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		fornitoreService.deleteFornitoreById(codiceAzienda);
		ResponseDto<FornitoreDto> genericResponse = ResponseDto.<FornitoreDto>builder().data(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping("upload-logo/{codiceAzienda}")
	public ResponseEntity<ResponseDto<String>> uploadLogo(@PathVariable(name = "codiceAzienda") String codiceAzienda, @RequestParam("file") MultipartFile file) {
		try {
			fileService.uploadLogoFornitore(codiceAzienda, file);
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
		LogoAzienda data = fileService.getLogoFornitore(codiceAzienda);
		Resource resource = new ByteArrayResource(data.getLogo());
		String filename = data.getFilename();
		return ResponseEntity.ok()
		        .contentType(MediaType.APPLICATION_OCTET_STREAM)
		        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ filename + "\"")
		        .body(resource);
	}
	
	@GetMapping(value = "/read-logo/{codiceAzienda}")
	public ResponseEntity<ResponseDto<LogoAziendaDto>> readProfileImage(@PathVariable String codiceAzienda) {
		LogoAzienda logo = fileService.getLogoFornitore(codiceAzienda);
		LogoAziendaDto logoDto = dtoEntityMapper.entityToDto(logo);
		ResponseDto<LogoAziendaDto> genericResponse = ResponseDto.<LogoAziendaDto>builder().data(logoDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
}
