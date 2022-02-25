package com.perigea.tracker.timesheet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.commons.dto.ContactDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.timesheet.service.ContactDetailsService;

@RestController
@RequestMapping("/recapiti")
public class ContactDetailsController {

	@Autowired
	private ContactDetailsService contactDetailsService;

	@GetMapping(value = "/contact-details/read-by-id/{contattoId}")
	public ResponseEntity<ResponseDto<ContactDto>> readUserContactDetails(
			@PathVariable(name = "userId") String userId) {
		ContactDto contactDetails = contactDetailsService.readUserContactDetails(userId);
		ResponseDto<ContactDto> genericResponse = ResponseDto.<ContactDto>builder().data(contactDetails).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/contact-details/read-all-group-contact-details/{groupId}")
	public ResponseEntity<ResponseDto<List<ContactDto>>> readAllContactDetails(
			@PathVariable(name = "groupId") Long groupId) {
		List<ContactDto> dtos = contactDetailsService.readAllContactDetails(groupId);
		ResponseDto<List<ContactDto>> genericResponse = ResponseDto.<List<ContactDto>>builder().data(dtos).build();
		return ResponseEntity.ok(genericResponse);
	}

}
