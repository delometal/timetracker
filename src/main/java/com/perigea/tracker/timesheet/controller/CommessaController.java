package com.perigea.tracker.timesheet.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.commons.dto.CommessaDto;
import com.perigea.tracker.commons.dto.CommessaEstensioneDto;
import com.perigea.tracker.commons.dto.CommessaFatturabileDto;
import com.perigea.tracker.commons.dto.CommessaNonFatturabileDto;
import com.perigea.tracker.commons.dto.OrdineCommessaDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.commons.dto.wrapper.CommessaFatturabileDtoWrapper;
import com.perigea.tracker.commons.dto.wrapper.CommessaNonFatturabileDtoWrapper;
import com.perigea.tracker.timesheet.entity.Cliente;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.CommessaEstensione;
import com.perigea.tracker.timesheet.entity.CommessaFatturabile;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.entity.OrdineCommessa;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.ClienteService;
import com.perigea.tracker.timesheet.service.CommessaService;

@RestController
@RequestMapping("/commesse")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class CommessaController {

	@Autowired
	private CommessaService commessaService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@PostMapping(value = "/create-commessa-fatturabile")
	public ResponseEntity<ResponseDto<CommessaFatturabileDto>> createCommessaFatturabile(
			@RequestBody CommessaFatturabileDtoWrapper wrapper) {
		CommessaFatturabile commessa = dtoEntityMapper.dtoToEntity(wrapper.getCommessaFatturabile());
		Cliente cliente = dtoEntityMapper.dtoToEntity(wrapper.getCliente());

		CommessaFatturabile commessaEntity = commessaService.createCommessaFatturabile(commessa, cliente);
		CommessaFatturabileDto commessaDto = dtoEntityMapper.entityToDto(commessaEntity);
		ResponseDto<CommessaFatturabileDto> genericDto = ResponseDto.<CommessaFatturabileDto>builder().data(commessaDto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	@PostMapping(value = "/create-commessa-non-fatturabile")
	public ResponseEntity<ResponseDto<CommessaNonFatturabileDto>> createCommessaNonFatturabile(
			@RequestBody CommessaNonFatturabileDtoWrapper wrapper) {
		CommessaNonFatturabile commessa = dtoEntityMapper.dtoToEntity(wrapper.getCommessaNonFatturabile());
		commessa = commessaService.saveCommessaNonFatturabile(commessa);
		CommessaNonFatturabileDto commessaDto = dtoEntityMapper.entityToDto(commessa);
		ResponseDto<CommessaNonFatturabileDto> genericDto = ResponseDto.<CommessaNonFatturabileDto>builder()
				.data(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}

	@PostMapping(value = "/create-estensione-commessa")
	public ResponseEntity<ResponseDto<CommessaEstensioneDto>> createCommessaEstensione(
			@RequestBody CommessaEstensioneDto estensione) {
		CommessaEstensione entity = dtoEntityMapper.dtoToEntity(estensione);
		CommessaFatturabile commessaFatturabile = commessaService
				.readCommessaFatturabile(estensione.getCodiceCommessa());
		commessaFatturabile.setTotaleEstensioni(commessaFatturabile.getTotaleEstensioni() + 1);
		commessaFatturabile.setDataFineCommessa(estensione.getDataFineEstensione());
		commessaService.updateCommessaFatturabile(commessaFatturabile);
		CommessaEstensione res = commessaService.createEstensioneCommessa(entity);
		CommessaEstensioneDto estensioneDto = dtoEntityMapper.entityToDto(res);
		ResponseDto<CommessaEstensioneDto> genericDto = ResponseDto.<CommessaEstensioneDto>builder().data(estensioneDto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	@PutMapping(value = "/update-commessa-fatturabile")
	public ResponseEntity<ResponseDto<CommessaFatturabileDto>> updateCommessaFatturabile(
			@RequestBody CommessaFatturabileDtoWrapper wrapper) {
		CommessaFatturabile commessa = dtoEntityMapper.dtoToEntity(wrapper.getCommessaFatturabile());
		Cliente cliente = clienteService.readClienteById(wrapper.getCliente().getCodiceAzienda());
		commessa.setCliente(cliente);
		commessa = commessaService.updateCommessaFatturabile(commessa);
		CommessaFatturabileDto commessaDto = dtoEntityMapper.entityToDto(commessa);
		ResponseDto<CommessaFatturabileDto> genericDto = ResponseDto.<CommessaFatturabileDto>builder().data(commessaDto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	@PutMapping(value = "/update-commessa-non-fatturabile")
	public ResponseEntity<ResponseDto<CommessaNonFatturabileDto>> updateCommessaNonFatturabile(
			@RequestBody CommessaNonFatturabileDtoWrapper wrapper) {
		CommessaNonFatturabile commessa = dtoEntityMapper.dtoToEntity(wrapper.getCommessaNonFatturabile());
		commessa.setCliente(clienteService.loadClientePerigea());
		commessa = commessaService.saveCommessaNonFatturabile(commessa);
		CommessaNonFatturabileDto commessaDto = dtoEntityMapper.entityToDto(commessa);
		ResponseDto<CommessaNonFatturabileDto> genericDto = ResponseDto.<CommessaNonFatturabileDto>builder()
				.data(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}

	@PostMapping(value = "/create-ordine-commessa")
	public ResponseEntity<ResponseDto<OrdineCommessaDto>> createOrdineCommessa(
			@RequestBody CommessaFatturabileDtoWrapper wrapper) {
		CommessaFatturabile commessa = dtoEntityMapper.dtoToEntity(wrapper.getCommessaFatturabile());
		OrdineCommessa ordineCommessa = dtoEntityMapper.dtoToEntity(wrapper.getOrdineCommessa());
		Cliente cliente = dtoEntityMapper.dtoToEntity(wrapper.getCliente());
		ordineCommessa = commessaService.createOrdineCommessa(ordineCommessa, commessa, cliente);
		OrdineCommessaDto ordineCommessaDto = dtoEntityMapper.entityToDto(ordineCommessa);
		ResponseDto<OrdineCommessaDto> genericDto = ResponseDto.<OrdineCommessaDto>builder().data(ordineCommessaDto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	@DeleteMapping(value = "/delete-commessa-fatturabile/{codiceCommessa}")
	public ResponseEntity<ResponseDto<CommessaFatturabileDto>> deleteCommessaFatturabile(
			@PathVariable(name = "codiceCommessa") String codiceCommessa) {
		CommessaFatturabile commessaEntity = commessaService.readCommessaFatturabile(codiceCommessa);
		CommessaFatturabileDto commessaDto = dtoEntityMapper.entityToDto(commessaEntity);
		commessaService.deleteCommessaFatturabile(codiceCommessa);
		ResponseDto<CommessaFatturabileDto> genericDto = ResponseDto.<CommessaFatturabileDto>builder().data(commessaDto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	@DeleteMapping(value = "/delete-commessa-non-fatturabile/{codiceCommessa}")
	public ResponseEntity<ResponseDto<CommessaNonFatturabileDto>> deleteCommessaNonFatturabile(
			@PathVariable(name = "codiceCommessa") String codiceCommessa) {
		CommessaNonFatturabile commessaEntity = commessaService.readCommessaNonFatturabile(codiceCommessa);
		CommessaNonFatturabileDto commessaDto = dtoEntityMapper.entityToDto(commessaEntity);
		commessaService.deleteCommessaNonFatturabile(codiceCommessa);
		ResponseDto<CommessaNonFatturabileDto> genericDto = ResponseDto.<CommessaNonFatturabileDto>builder()
				.data(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/read-commessa-fatturabile/{codiceCommessa}")
	public ResponseEntity<ResponseDto<CommessaFatturabileDto>> readCommessaFatturabile(
			@PathVariable(name = "codiceCommessa") String codiceCommessa) {
		CommessaFatturabile commessaEntity = commessaService.readCommessaFatturabile(codiceCommessa);
		CommessaFatturabileDto commessaDto = dtoEntityMapper.entityToDto(commessaEntity);
		ResponseDto<CommessaFatturabileDto> genericDto = ResponseDto.<CommessaFatturabileDto>builder().data(commessaDto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/read-commessa-non-fatturabile/{codiceCommessa}")
	public ResponseEntity<ResponseDto<CommessaNonFatturabileDto>> readCommessaNonFatturabile(
			@PathVariable(name = "codiceCommessa") String codiceCommessa) {
		CommessaNonFatturabile commessaEntity = commessaService.readCommessaNonFatturabile(codiceCommessa);
		CommessaNonFatturabileDto commessaDto = dtoEntityMapper.entityToDto(commessaEntity);
		ResponseDto<CommessaNonFatturabileDto> genericDto = ResponseDto.<CommessaNonFatturabileDto>builder()
				.data(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/read-estensione-commessa/{codiceCommessa}")
	public ResponseEntity<ResponseDto<List<CommessaEstensioneDto>>> readAllCommessaEstensione(
			@PathVariable(name = "codiceCommessa") String codiceCommessa) {
		List<CommessaEstensione> all = commessaService.readAllCommessaEstensione(codiceCommessa);
		List<CommessaEstensioneDto> allDto = dtoEntityMapper.entityToDtoList(all);
		ResponseDto<List<CommessaEstensioneDto>> genericDto = ResponseDto.<List<CommessaEstensioneDto>>builder()
				.data(allDto).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/read-estensione-commessa/{codiceCommessa}/{dataEstensione}")
	public ResponseEntity<ResponseDto<CommessaEstensioneDto>> readCommessaEstensione(
			@PathVariable(name = "codiceCommessa") String codiceCommessa,
			@PathVariable(name = "dataEstensione") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataEstensione) {

		CommessaEstensione entity = commessaService.findById(codiceCommessa, dataEstensione);
		CommessaEstensioneDto dto = dtoEntityMapper.entityToDto(entity);
		ResponseDto<CommessaEstensioneDto> genericDto = ResponseDto.<CommessaEstensioneDto>builder().data(dto).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/checkSpecification/{importoTotAnno}/{importoOrdine}")
	public ResponseEntity<ResponseDto<List<CommessaFatturabileDto>>> readCommessaFatturabile(
			@PathVariable Double importoTotAnno, @PathVariable Double importoOrdine) {
		List<CommessaFatturabile> commesseEntity = commessaService.searchCommesseAfterThatImports(importoTotAnno,
				importoOrdine);
		List<CommessaFatturabileDto> commesseDto = dtoEntityMapper.entityToCommessaFattDtoList(commesseEntity);
		ResponseDto<List<CommessaFatturabileDto>> genericDto = ResponseDto.<List<CommessaFatturabileDto>>builder()
				.data(commesseDto).build();
		return ResponseEntity.ok(genericDto);
	}

	@DeleteMapping(value = "/delete-estensione-commessa/{codiceCommessa}/{dataEstensione}")
	public ResponseEntity<ResponseDto<CommessaEstensioneDto>> deleteEstensioneCommessa(
			@PathVariable(name = "codiceCommessa") String codiceCommessa,
			@PathVariable(name = "dataEstensione") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataEstensione) {
		CommessaEstensione commessaEntity = commessaService.findById(codiceCommessa, dataEstensione);
		CommessaEstensioneDto commessaDto = dtoEntityMapper.entityToDto(commessaEntity);
		commessaService.deleteEstensioneCommessa(codiceCommessa, dataEstensione);
		CommessaFatturabile commessaFatturabile = commessaService.readCommessaFatturabile(codiceCommessa);
		commessaFatturabile.setTotaleEstensioni(commessaFatturabile.getTotaleEstensioni() - 1);
		commessaService.updateCommessaFatturabile(commessaFatturabile);
		ResponseDto<CommessaEstensioneDto> genericDto = ResponseDto.<CommessaEstensioneDto>builder().data(commessaDto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	@PutMapping(value = "/update-estensione-commessa")
	public ResponseEntity<ResponseDto<CommessaEstensioneDto>> updateCommessaEstensione(
			@RequestBody CommessaEstensioneDto estensione) {
		CommessaEstensione commessaEntity = dtoEntityMapper.dtoToEntity(estensione);
		CommessaFatturabile commessaFatturabile = commessaService
				.readCommessaFatturabile(estensione.getCodiceCommessa());
		CommessaEstensione res = commessaService.updateEstensioneCommessa(commessaEntity);
		commessaFatturabile.setDataFineCommessa(estensione.getDataFineEstensione());
		commessaService.updateCommessaFatturabile(commessaFatturabile);
		CommessaEstensioneDto estensioneDto = dtoEntityMapper.entityToDto(res);
		ResponseDto<CommessaEstensioneDto> genericDto = ResponseDto.<CommessaEstensioneDto>builder().data(estensioneDto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/read-all-fatturabili")
	public ResponseEntity<ResponseDto<List<CommessaFatturabileDto>>> readAllCommesseFatturabili() {
		List<CommessaFatturabile> commesse = commessaService.getAllCommesseFatturabili();
		List<CommessaFatturabileDto> dtos = dtoEntityMapper.entityToCommessaFattDtoList(commesse);
		ResponseDto<List<CommessaFatturabileDto>> genericResponse = ResponseDto.<List<CommessaFatturabileDto>>builder()
				.data(dtos).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-all-non-fatturabili")
	public ResponseEntity<ResponseDto<List<CommessaNonFatturabileDto>>> readAllCommesseNonFatturabili() {
		List<CommessaNonFatturabile> commesse = commessaService.getAllCommesseNonFatturabili();
		List<CommessaNonFatturabileDto> dtos = dtoEntityMapper.entityToCommessaNoFattDtoList(commesse);
		ResponseDto<List<CommessaNonFatturabileDto>> genericResponse = ResponseDto
				.<List<CommessaNonFatturabileDto>>builder().data(dtos).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-all-commesse")
	public ResponseEntity<ResponseDto<List<CommessaDto>>> readAllCommesse() {
		List<Commessa> commesse = commessaService.getAllCommesse();
		List<CommessaDto> dtos = dtoEntityMapper.entityToCommessaDtoList(commesse);
		ResponseDto<List<CommessaDto>> genericResponse = ResponseDto.<List<CommessaDto>>builder().data(dtos).build();
		return ResponseEntity.ok(genericResponse);
	}

}
