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

import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.timesheet.entity.CurriculumVitae;
import com.perigea.tracker.timesheet.service.FileService;

@RestController
@RequestMapping("/curriculum")
@CrossOrigin(allowedHeaders = "*", origins = "*", originPatterns = "*", exposedHeaders = "*")
public class CurriculumController {

	@Autowired
	private FileService fileService;

	@PostMapping("/{codicePersona}")
	public ResponseEntity<ResponseDto<String>> uploadCurriculum(@PathVariable(name = "codicePersona") String codicePersona, @RequestParam("file") MultipartFile file) {
		try {
			fileService.uploadCurriculum(codicePersona, file);
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
	@GetMapping(value="/archivio/{codicePersona}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<?> downloadCurriculum(@PathVariable String codicePersona) {
		CurriculumVitae data = fileService.getCurriculum(codicePersona);
		Resource resource = new ByteArrayResource(data.getCv());
		String filename = data.getFilename();
		return ResponseEntity.ok()
		        .contentType(MediaType.APPLICATION_OCTET_STREAM)
		        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ filename + "\"")
		        .body(resource);
	}

}