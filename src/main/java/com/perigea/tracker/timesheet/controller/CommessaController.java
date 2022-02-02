package com.perigea.tracker.timesheet.controller;

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

import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.dto.CommessaFatturabileDto;
import com.perigea.tracker.timesheet.dto.CommessaNonFatturabileDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.OrdineCommessaDto;
import com.perigea.tracker.timesheet.dto.wrapper.CommessaFatturabileDtoWrapper;
import com.perigea.tracker.timesheet.dto.wrapper.CommessaNonFatturabileDtoWrapper;
import com.perigea.tracker.timesheet.entity.Cliente;
import com.perigea.tracker.timesheet.entity.CommessaFatturabile;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.entity.OrdineCommessa;
import com.perigea.tracker.timesheet.service.ClienteService;
import com.perigea.tracker.timesheet.service.CommessaService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@RestController
@RequestMapping("/commesse")
public class CommessaController {

	@Autowired
	private CommessaService commessaService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@PostMapping(value = "/create-commessa-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaFatturabileDto>> createCommessaFatturabile(@RequestBody CommessaFatturabileDtoWrapper wrapper) {
		CommessaFatturabile commessa = dtoEntityMapper.dtoToEntity(wrapper.getCommessaFatturabile());
		Cliente cliente = dtoEntityMapper.dtoToEntity(wrapper.getCliente());

		CommessaFatturabile commessaEntity = commessaService.createCommessaFatturabile(commessa, cliente);
		CommessaFatturabileDto commessaDto = dtoEntityMapper.entityToDto(commessaEntity);
		GenericWrapperResponse<CommessaFatturabileDto> genericDto = GenericWrapperResponse.<CommessaFatturabileDto>builder().dataRichiesta(Utils.now()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}

	@PostMapping(value = "/create-commessa-non-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaNonFatturabileDto>> createCommessaNonFatturabile(@RequestBody CommessaNonFatturabileDtoWrapper wrapper) {
		CommessaNonFatturabile commessa = dtoEntityMapper.dtoToEntity(wrapper.getCommessaNonFatturabile());
		commessa.setCliente(clienteService.loadClientePerigea());
		commessa.setCodiceCommessa(Utils.uuid());
		commessa = commessaService.saveCommessaNonFatturabile(commessa);
		CommessaNonFatturabileDto commessaDto = dtoEntityMapper.entityToDto(commessa);
		GenericWrapperResponse<CommessaNonFatturabileDto> genericDto = GenericWrapperResponse.<CommessaNonFatturabileDto>builder()
				.dataRichiesta(Utils.now()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}

	@PutMapping(value = "/update-commessa-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaFatturabileDto>> updateCommessaNonFatturabile(@RequestBody CommessaFatturabileDtoWrapper wrapper) {
		CommessaFatturabile commessa = dtoEntityMapper.dtoToEntity(wrapper.getCommessaFatturabile());
		Cliente anagrafica = clienteService.readCliente(wrapper.getCliente().getPartitaIva());
		commessa.setCliente(anagrafica);
		commessa = commessaService.updateCommessaFatturabile(commessa);
		CommessaFatturabileDto commessaDto = dtoEntityMapper.entityToDto(commessa);
		GenericWrapperResponse<CommessaFatturabileDto> genericDto = GenericWrapperResponse.<CommessaFatturabileDto>builder()
				.dataRichiesta(Utils.now()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@PostMapping(value = "/update-commessa-non-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaNonFatturabileDto>> updateCommessaNonFatturabile(@RequestBody CommessaNonFatturabileDtoWrapper wrapper) {
		CommessaNonFatturabile commessa = dtoEntityMapper.dtoToEntity(wrapper.getCommessaNonFatturabile());
		commessa.setCliente(clienteService.loadClientePerigea());
		commessa = commessaService.saveCommessaNonFatturabile(commessa);
		CommessaNonFatturabileDto commessaDto = dtoEntityMapper.entityToDto(commessa);
		GenericWrapperResponse<CommessaNonFatturabileDto> genericDto = GenericWrapperResponse.<CommessaNonFatturabileDto>builder()
				.dataRichiesta(Utils.now()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@PostMapping(value = "/create-ordine-commessa")
	public ResponseEntity<GenericWrapperResponse<OrdineCommessaDto>> createOrdineCommessa(@RequestBody CommessaFatturabileDtoWrapper wrapper,  @RequestParam String idCliente) {
		CommessaFatturabile commessa = dtoEntityMapper.dtoToEntity(wrapper.getCommessaFatturabile());
		OrdineCommessa ordineCommessa = dtoEntityMapper.dtoToEntity(wrapper.getOrdineCommessa());
		Cliente cliente = clienteService.readCliente(wrapper.getCliente().getPartitaIva());
		ordineCommessa = commessaService.createOrdineCommessa(ordineCommessa, commessa, cliente);
		OrdineCommessaDto ordineCommessaDto = dtoEntityMapper.entityToDto(ordineCommessa);
		GenericWrapperResponse<OrdineCommessaDto> genericDto = GenericWrapperResponse.<OrdineCommessaDto>builder()
				.dataRichiesta(Utils.now()).risultato(ordineCommessaDto).build();
		return ResponseEntity.ok(genericDto);
	}

	@DeleteMapping(value = "/delete-commessa-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaFatturabileDto>> deleteCommessaFatturabile(@RequestParam String codiceCommessa) {
		CommessaFatturabile commessaEntity = commessaService.readCommessaFatturabile(codiceCommessa);
		CommessaFatturabileDto commessaDto = dtoEntityMapper.entityToDto(commessaEntity);
		commessaService.deleteCommessaFatturabile(codiceCommessa);
		GenericWrapperResponse<CommessaFatturabileDto> genericDto = GenericWrapperResponse.<CommessaFatturabileDto>builder()
				.dataRichiesta(Utils.now()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@DeleteMapping(value = "/delete-commessa-non-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaNonFatturabileDto>> deleteCommessaNonFatturabile(@RequestParam String codiceCommessa) {
		CommessaNonFatturabile commessaEntity = commessaService.readCommessaNonFatturabile(codiceCommessa);
		CommessaNonFatturabileDto commessaDto = dtoEntityMapper.entityToDto(commessaEntity);
		commessaService.deleteCommessaNonFatturabile(codiceCommessa);
		GenericWrapperResponse<CommessaNonFatturabileDto> genericDto = GenericWrapperResponse.<CommessaNonFatturabileDto>builder()
				.dataRichiesta(Utils.now()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@GetMapping(value = "/read-commessa-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaFatturabileDto>> readCommessaFatturabile(@RequestParam String codiceCommessa) {
		CommessaFatturabile commessaEntity = commessaService.readCommessaFatturabile(codiceCommessa);
		CommessaFatturabileDto commessaDto = dtoEntityMapper.entityToDto(commessaEntity);
		GenericWrapperResponse<CommessaFatturabileDto> genericDto = GenericWrapperResponse.<CommessaFatturabileDto>builder()
				.dataRichiesta(Utils.now()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@GetMapping(value = "/read-commessa-non-fatturabile")
	public ResponseEntity<GenericWrapperResponse<CommessaNonFatturabileDto>> readCommessaNonFatturabile(@RequestParam String codiceCommessa) {
		CommessaNonFatturabile commessaEntity = commessaService.readCommessaNonFatturabile(codiceCommessa);
		CommessaNonFatturabileDto commessaDto = dtoEntityMapper.entityToDto(commessaEntity);
		GenericWrapperResponse<CommessaNonFatturabileDto> genericDto = GenericWrapperResponse.<CommessaNonFatturabileDto>builder()
				.dataRichiesta(Utils.now()).risultato(commessaDto).build();
		return ResponseEntity.ok(genericDto);
	}
	
}
