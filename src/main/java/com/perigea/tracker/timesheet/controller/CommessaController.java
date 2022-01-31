package com.perigea.tracker.timesheet.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.CommessaFatturabileDto;
import com.perigea.tracker.timesheet.dto.CommessaNonFatturabileDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.OrdineCommessaDto;
import com.perigea.tracker.timesheet.dto.wrapper.CommessaFatturabileDtoWrapper;
import com.perigea.tracker.timesheet.dto.wrapper.CommessaNonFatturabileDtoWrapper;
import com.perigea.tracker.timesheet.entity.AnagraficaCliente;
import com.perigea.tracker.timesheet.entity.CommessaFatturabile;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.entity.OrdineCommessa;
import com.perigea.tracker.timesheet.service.ClienteService;
import com.perigea.tracker.timesheet.service.CommessaService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;
import com.perigea.tracker.timesheet.utility.TSUtils;

@RestController
@RequestMapping("/commesse")
public class CommessaController {

	@Autowired
	private CommessaService commessaService;
	
	@Autowired
	private ClienteService clienteService;

	@PostMapping(value = "/create-commessa-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaFatturabileDto>> createCommessaFatturabile(@RequestBody CommessaFatturabileDtoWrapper wrapper) {
		CommessaFatturabile commessa = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaFatturabile(wrapper.getCommessaFatturabile());
		AnagraficaCliente anagraficaCliente = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaCliente(wrapper.getAnagraficaCliente());

		CommessaFatturabile commessaEntity = commessaService.createCommessaFatturabile(commessa, anagraficaCliente);
		CommessaFatturabileDto commessaDto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaFatturabile(commessaEntity);
		GenericWrapperResponse<CommessaFatturabileDto> genericDto = GenericWrapperResponse.<CommessaFatturabileDto>builder().dataRichiesta(new Date()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}

	@PostMapping(value = "/create-commessa-non-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaNonFatturabileDto>> createCommessaNonFatturabile(@RequestBody CommessaNonFatturabileDtoWrapper wrapper) {
		CommessaNonFatturabile commessa = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaNonFatturabile(wrapper.getCommessaNonFatturabile());
		commessa.setCliente(clienteService.loadAnagraficaClientePerigea());
		commessa.setCodiceCommessa(TSUtils.uuid());
		commessa = commessaService.saveCommessaNonFatturabile(commessa);
		CommessaNonFatturabileDto commessaDto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaNonFatturabile(commessa);
		GenericWrapperResponse<CommessaNonFatturabileDto> genericDto = GenericWrapperResponse.<CommessaNonFatturabileDto>builder()
				.dataRichiesta(new Date()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}

	@PutMapping(value = "/update-commessa-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaFatturabileDto>> updateCommessaNonFatturabile(@RequestBody CommessaFatturabileDtoWrapper wrapper) {
		CommessaFatturabile commessa = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaFatturabile(wrapper.getCommessaFatturabile());
		AnagraficaCliente anagrafica = clienteService.readAnagraficaCliente(wrapper.getAnagraficaCliente().getPartitaIva());
		commessa.setCliente(anagrafica);
		commessa = commessaService.updateCommessaFatturabile(commessa);
		CommessaFatturabileDto commessaDto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaFatturabile(commessa);
		GenericWrapperResponse<CommessaFatturabileDto> genericDto = GenericWrapperResponse.<CommessaFatturabileDto>builder()
				.dataRichiesta(new Date()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@PostMapping(value = "/update-commessa-non-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaNonFatturabileDto>> updateCommessaNonFatturabile(@RequestBody CommessaNonFatturabileDtoWrapper wrapper) {
		CommessaNonFatturabile commessa = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaNonFatturabile(wrapper.getCommessaNonFatturabile());
		commessa.setCliente(clienteService.loadAnagraficaClientePerigea());
		commessa = commessaService.saveCommessaNonFatturabile(commessa);
		CommessaNonFatturabileDto commessaDto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaNonFatturabile(commessa);
		GenericWrapperResponse<CommessaNonFatturabileDto> genericDto = GenericWrapperResponse.<CommessaNonFatturabileDto>builder()
				.dataRichiesta(new Date()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@PostMapping(value = "/create-ordine-commessa")
	public ResponseEntity<GenericWrapperResponse<OrdineCommessaDto>> createOrdineCommessa(@RequestBody CommessaFatturabileDtoWrapper wrapper,  @RequestParam String idCliente) {
		CommessaFatturabile commessa = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaFatturabile(wrapper.getCommessaFatturabile());
		OrdineCommessa ordineCommessa = DtoEntityMapper.INSTANCE.fromDtoToEntityOrdineCommessa(wrapper.getOrdineCommessa());
		AnagraficaCliente anagraficaCliente = clienteService.readAnagraficaCliente(wrapper.getAnagraficaCliente().getPartitaIva());
		ordineCommessa = commessaService.createOrdineCommessa(ordineCommessa, commessa, anagraficaCliente);
		OrdineCommessaDto ordineCommessaDto = DtoEntityMapper.INSTANCE.fromEntityToDtoOrdineCommessa(ordineCommessa);
		GenericWrapperResponse<OrdineCommessaDto> genericDto = GenericWrapperResponse.<OrdineCommessaDto>builder()
				.dataRichiesta(new Date()).risultato(ordineCommessaDto).build();
		return ResponseEntity.ok(genericDto);
	}

	@DeleteMapping(value = "/delete-commessa-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaFatturabileDto>> deleteCommessaFatturabile(@RequestParam String codiceCommessa) {
		CommessaFatturabile commessaEntity = commessaService.readCommessaFatturabile(codiceCommessa);
		CommessaFatturabileDto commessaDto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaFatturabile(commessaEntity);
		commessaService.deleteCommessaFatturabile(codiceCommessa);
		GenericWrapperResponse<CommessaFatturabileDto> genericDto = GenericWrapperResponse.<CommessaFatturabileDto>builder()
				.dataRichiesta(new Date()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@DeleteMapping(value = "/delete-commessa-non-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaNonFatturabileDto>> deleteCommessaNonFatturabile(@RequestParam String codiceCommessa) {
		CommessaNonFatturabile commessaEntity = commessaService.readCommessaNonFatturabile(codiceCommessa);
		CommessaNonFatturabileDto commessaDto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaNonFatturabile(commessaEntity);
		commessaService.deleteCommessaNonFatturabile(codiceCommessa);
		GenericWrapperResponse<CommessaNonFatturabileDto> genericDto = GenericWrapperResponse.<CommessaNonFatturabileDto>builder()
				.dataRichiesta(new Date()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@GetMapping(value = "/read-commessa-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaFatturabileDto>> readCommessaFatturabile(@RequestParam String codiceCommessa) {
		CommessaFatturabile commessaEntity = commessaService.readCommessaFatturabile(codiceCommessa);
		CommessaFatturabileDto commessaDto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaFatturabile(commessaEntity);
		GenericWrapperResponse<CommessaFatturabileDto> genericDto = GenericWrapperResponse.<CommessaFatturabileDto>builder()
				.dataRichiesta(new Date()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@GetMapping(value = "/read-commessa-non-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaNonFatturabileDto>> readCommessaNonFatturabile(@RequestParam String codiceCommessa) {
		CommessaNonFatturabile commessaEntity = commessaService.readCommessaNonFatturabile(codiceCommessa);
		CommessaNonFatturabileDto commessaDto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaNonFatturabile(commessaEntity);
		GenericWrapperResponse<CommessaNonFatturabileDto> genericDto = GenericWrapperResponse.<CommessaNonFatturabileDto>builder()
				.dataRichiesta(new Date()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}
	
}
