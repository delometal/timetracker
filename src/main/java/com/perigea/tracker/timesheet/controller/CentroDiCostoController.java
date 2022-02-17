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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.commons.dto.CentroDiCostoDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.CentroDiCosto;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.CentroDiCostoService;

@RestController
@RequestMapping("/backoffice/centri-di-costo")
public class CentroDiCostoController {

	@Autowired
	private CentroDiCostoService centroDiCostoService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@PostMapping(value = "/create")
	public ResponseEntity<ResponseDto<CentroDiCostoDto>> createCentroDiCosto(@RequestBody CentroDiCostoDto centroDiCostoDto) {
		CentroDiCosto centroDiCosto = dtoEntityMapper.dtoToEntity(centroDiCostoDto);
		centroDiCosto = centroDiCostoService.createCentroDiCosto(centroDiCosto);
		CentroDiCostoDto dtoCentroDiCosto = dtoEntityMapper.entityToDto(centroDiCosto);
		ResponseDto<CentroDiCostoDto> genericDto = ResponseDto.<CentroDiCostoDto>builder()
				.timestamp(Utils.now()).data(dtoCentroDiCosto).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/read/{id}")
	public ResponseEntity<ResponseDto<CentroDiCostoDto>> readCentroDiCosto(@PathVariable("id") String id) {
		CentroDiCosto centroDiCosto = centroDiCostoService.readCentroDiCosto(id);
		CentroDiCostoDto dtoCentroDiCosto = dtoEntityMapper.entityToDto(centroDiCosto);
		ResponseDto<CentroDiCostoDto> genericDto = ResponseDto.<CentroDiCostoDto>builder()
				.timestamp(Utils.now()).data(dtoCentroDiCosto).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/search")
	public ResponseEntity<ResponseDto<List<CentroDiCostoDto>>> searchCentroDiCosto(@RequestParam("searchKey") String searchKey) {
		List<CentroDiCosto> centroDiCosto = centroDiCostoService.searchCentroDiCosto(searchKey);
		List<CentroDiCostoDto> dtoCentroDiCosto = dtoEntityMapper.entityToDtoCentroDiCostoList(centroDiCosto);
		ResponseDto<List<CentroDiCostoDto>> genericDto = ResponseDto.<List<CentroDiCostoDto>>builder()
				.timestamp(Utils.now()).data(dtoCentroDiCosto).build();
		return ResponseEntity.ok(genericDto);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<ResponseDto<CentroDiCostoDto>> updateCentroDiCosto(@RequestBody CentroDiCostoDto centroDiCostoDto) {
		CentroDiCosto centroDiCosto = dtoEntityMapper.dtoToEntity(centroDiCostoDto);
		centroDiCosto = centroDiCostoService.updateCentroDiCosto(centroDiCosto);
		CentroDiCostoDto dtoCentroDiCosto = dtoEntityMapper.entityToDto(centroDiCosto);
		ResponseDto<CentroDiCostoDto> genericDto = ResponseDto.<CentroDiCostoDto>builder()
				.timestamp(Utils.now()).data(dtoCentroDiCosto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<ResponseDto<CentroDiCostoDto>> deleteCentroDiCosto(@PathVariable("id") String id) {
		CentroDiCosto centroDiCosto = centroDiCostoService.readCentroDiCosto(id);
		CentroDiCostoDto centroDiCostoDto = dtoEntityMapper.entityToDto(centroDiCosto);
		centroDiCostoService.deleteCentroDiCosto(id);
		ResponseDto<CentroDiCostoDto> genericDto = ResponseDto.<CentroDiCostoDto>builder()
				.timestamp(Utils.now()).data(centroDiCostoDto).build();
		return ResponseEntity.ok(genericDto);
	}
	
}
