package com.perigea.tracker.timesheet.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.CommessaDto;
import com.perigea.tracker.timesheet.dto.CommessaFatturabileDto;
import com.perigea.tracker.timesheet.dto.CommessaNonFatturabileDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.service.CommessaService;
import com.perigea.tracker.timesheet.wrapper.CommessaFatturabileWrapper;
import com.perigea.tracker.timesheet.wrapper.CommessaNonFatturabileWrapper;


@RestController
public class CommessaController {

	@Autowired
	private CommessaService commessaService;

	

	// Metodo per creare un timesheet
//	@PostMapping(value = "/create1-commessa-fatturabile")
//	public ResponseEntity <GenericWrapperResponse<CommessaFatturabileDto>> create1CommessaFatturabile(@RequestBody CommessaFatturabileWrapper wrapper) {
//		CommessaFatturabileDto dto=commessaService.createCommessaFatturabile(wrapper.getCommessaFatturabileDto(),wrapper.getAnagraficaCliente(),wrapper.getDtoCommessa());
//		GenericWrapperResponse<CommessaFatturabileDto> genericDto = GenericWrapperResponse.<CommessaFatturabileDto>builder()
//				.dataRichiesta(new Date())
//				.risultato(dto)
//				.build();
//		return ResponseEntity.ok(genericDto);
//	}
	
	// Metodo per creare un timesheet
		@PostMapping(value = "/create-commessa-fatturabile")
		public ResponseEntity <GenericWrapperResponse<CommessaFatturabileDto>> createCommessaFatturabile(@RequestBody CommessaFatturabileWrapper commessaParam) throws NoSuchAlgorithmException, UnsupportedEncodingException {
			CommessaFatturabileDto dto=commessaService.createCommessaFatturabile(commessaParam);
			GenericWrapperResponse<CommessaFatturabileDto>genericDto=GenericWrapperResponse.<CommessaFatturabileDto>builder()
					.dataRichiesta(new Date())
					.risultato(dto)
					.build();
			return ResponseEntity.ok(genericDto);
		}
	
	// Metodo per creare un timesheet
	@PostMapping(value = "/create-commessa-non-fatturabile")
	public ResponseEntity <GenericWrapperResponse<CommessaNonFatturabileDto>> createCommessaNonFatturabile(@RequestBody CommessaNonFatturabileWrapper body) {
		CommessaNonFatturabileDto dto=commessaService.createCommessaNonFatturabile(body.getCommessaParam(),body.getCommessa());
		GenericWrapperResponse<CommessaNonFatturabileDto> genericDto = GenericWrapperResponse.<CommessaNonFatturabileDto>builder()
				.dataRichiesta(new Date())
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	// Metodo per creare un timesheet
	@GetMapping(value = "/delete-commessa-non-fatturabile")
	public ResponseEntity <GenericWrapperResponse<CommessaNonFatturabileDto>> deleteCommessaNonFatturabile(@RequestParam String codiceCommessa) {
		CommessaNonFatturabileDto dto = commessaService.deleteCommessaNonFatturabile(codiceCommessa);
		GenericWrapperResponse<CommessaNonFatturabileDto> genericDto = GenericWrapperResponse.<CommessaNonFatturabileDto>builder()
				.dataRichiesta(new Date())
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	// Metodo per creare un timesheet
	@PostMapping(value = "/create-commessa")
	public ResponseEntity <GenericWrapperResponse<CommessaDto>> createCommessa(@RequestBody CommessaDto commessaParam) {
		CommessaDto dto=commessaService.createCommessa(commessaParam);
		Date date=new Date();
		GenericWrapperResponse<CommessaDto> genericDto = GenericWrapperResponse.<CommessaDto>builder()
				.dataRichiesta(date)
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	// Metodo per creare un timesheet
	@GetMapping(value = "/delete-commessa")
	public ResponseEntity <GenericWrapperResponse<CommessaDto>> deleteCommessa(@RequestParam String codiceCommessa) {
		CommessaDto dto = commessaService.deleteCommessa(codiceCommessa);
		GenericWrapperResponse<CommessaDto> genericDto = GenericWrapperResponse.<CommessaDto>builder()
				.dataRichiesta(new Date())
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericDto);
	}
}
