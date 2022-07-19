package com.perigea.tracker.timesheet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.perigea.tracker.commons.dto.ProfileImageDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.timesheet.entity.ProfileImage;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.FileService;

@RestController
@RequestMapping("/profile-image")
@CrossOrigin(allowedHeaders = "*", origins = "*", originPatterns = "*", exposedHeaders = "*")
public class ProfileImageController {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private DtoEntityMapper mapper;
	
	@PostMapping("/{codicePersona}")
	public ResponseEntity<ResponseDto<String>> uploadImmagineProfilo(
			@PathVariable(name = "codicePersona") String codicePersona, @RequestParam("file") MultipartFile file) {
		try {
			fileService.uploadProfileImage(codicePersona, file);
			ResponseDto<String> genericResponse = ResponseDto.<String>builder()
					.data("Uploaded the file successfully: " + file.getOriginalFilename()).build();
			return ResponseEntity.ok(genericResponse);
		} catch (Exception e) {
			ResponseDto<String> genericResponse = ResponseDto.<String>builder().data(e.getMessage())
					.code(HttpStatus.BAD_REQUEST.value()).build();
			return ResponseEntity.badRequest().body(genericResponse);
		}
	}
	
	@ResponseBody
	@GetMapping(value="/archivio/{codicePersona}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<?> downloadImmagineProfilo(@PathVariable String codicePersona) {
		ProfileImage data = fileService.getProfileImage(codicePersona);
		Resource resource = new ByteArrayResource(data.getImage());
		String filename = data.getFilename();
		return ResponseEntity.ok()
		        .contentType(MediaType.APPLICATION_OCTET_STREAM)
		        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ filename + "\"")
		        .body(resource);
	}
	
	
	
	@GetMapping(value = "/read/{codicePersona}")
	public ResponseEntity<ResponseDto<ProfileImageDto>> readProfileImage(@PathVariable String codicePersona) {
		ProfileImage image = fileService.getProfileImage(codicePersona);
		ProfileImageDto imageDto = mapper.entityToDto(image);
		ResponseDto<ProfileImageDto> genericResponse = ResponseDto.<ProfileImageDto>builder().data(imageDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	
}
