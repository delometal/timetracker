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
import com.perigea.tracker.commons.dto.GenericWrapperResponse;
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
	public ResponseEntity<GenericWrapperResponse<CentroDiCostoDto>> createCentroDiCosto(@RequestBody CentroDiCostoDto centroDiCostoDto) {
		CentroDiCosto centroDiCosto = dtoEntityMapper.dtoToEntity(centroDiCostoDto);
		centroDiCosto = centroDiCostoService.createCentroDiCosto(centroDiCosto);
		CentroDiCostoDto dtoCentroDiCosto = dtoEntityMapper.entityToDto(centroDiCosto);
		GenericWrapperResponse<CentroDiCostoDto> genericDto = GenericWrapperResponse.<CentroDiCostoDto>builder()
				.timestamp(Utils.now()).risultato(dtoCentroDiCosto).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/read/{id}")
	public ResponseEntity<GenericWrapperResponse<CentroDiCostoDto>> readCentroDiCosto(@PathVariable("id") String id) {
		CentroDiCosto centroDiCosto = centroDiCostoService.readCentroDiCosto(id);
		CentroDiCostoDto dtoCentroDiCosto = dtoEntityMapper.entityToDto(centroDiCosto);
		GenericWrapperResponse<CentroDiCostoDto> genericDto = GenericWrapperResponse.<CentroDiCostoDto>builder()
				.timestamp(Utils.now()).risultato(dtoCentroDiCosto).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/search")
	public ResponseEntity<GenericWrapperResponse<List<CentroDiCostoDto>>> searchCentroDiCosto(@RequestParam("searchKey") String searchKey) {
		List<CentroDiCosto> centroDiCosto = centroDiCostoService.searchCentroDiCosto(searchKey);
		List<CentroDiCostoDto> dtoCentroDiCosto = dtoEntityMapper.entityToDtoCentroDiCostoList(centroDiCosto);
		GenericWrapperResponse<List<CentroDiCostoDto>> genericDto = GenericWrapperResponse.<List<CentroDiCostoDto>>builder()
				.timestamp(Utils.now()).risultato(dtoCentroDiCosto).build();
		return ResponseEntity.ok(genericDto);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<GenericWrapperResponse<CentroDiCostoDto>> updateCentroDiCosto(@RequestBody CentroDiCostoDto centroDiCostoDto) {
		CentroDiCosto centroDiCosto = dtoEntityMapper.dtoToEntity(centroDiCostoDto);
		centroDiCosto = centroDiCostoService.updateCentroDiCosto(centroDiCosto);
		CentroDiCostoDto dtoCentroDiCosto = dtoEntityMapper.entityToDto(centroDiCosto);
		GenericWrapperResponse<CentroDiCostoDto> genericDto = GenericWrapperResponse.<CentroDiCostoDto>builder()
				.timestamp(Utils.now()).risultato(dtoCentroDiCosto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<GenericWrapperResponse<CentroDiCostoDto>> deleteCentroDiCosto(@PathVariable("id") String id) {
		CentroDiCosto centroDiCosto = centroDiCostoService.readCentroDiCosto(id);
		CentroDiCostoDto centroDiCostoDto = dtoEntityMapper.entityToDto(centroDiCosto);
		centroDiCostoService.deleteCentroDiCosto(id);
		GenericWrapperResponse<CentroDiCostoDto> genericDto = GenericWrapperResponse.<CentroDiCostoDto>builder()
				.timestamp(Utils.now()).risultato(centroDiCostoDto).build();
		return ResponseEntity.ok(genericDto);
	}
	
}
