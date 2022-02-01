package com.perigea.tracker.timesheet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.AnagraficaDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.InfoAutoDto;
import com.perigea.tracker.timesheet.dto.TimesheetRefDto;
import com.perigea.tracker.timesheet.dto.TimesheetResponseDto;
import com.perigea.tracker.timesheet.dto.wrapper.TimesheetWrapper;
import com.perigea.tracker.timesheet.entity.Consulente;
import com.perigea.tracker.timesheet.entity.DatiEconomiciDipendente;
import com.perigea.tracker.timesheet.entity.Dipendente;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.TimesheetMensileKey;
import com.perigea.tracker.timesheet.enums.EMese;
import com.perigea.tracker.timesheet.enums.StatoRichiestaType;
import com.perigea.tracker.timesheet.exception.TimesheetException;
import com.perigea.tracker.timesheet.service.DipendenteService;
import com.perigea.tracker.timesheet.service.TimesheetService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;
import com.perigea.tracker.timesheet.utility.TSUtils;

@RestController
@RequestMapping("/timesheet")
public class TimesheetController {

	@Autowired
	private TimesheetService timesheetService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@Autowired
	private DipendenteService dipendenteService;

	@PostMapping(value = "/create")
	public ResponseEntity<GenericWrapperResponse<TimesheetResponseDto>> createTimesheet(@RequestBody TimesheetWrapper wrapper) {
		Timesheet timesheet = timesheetService.createTimesheet(wrapper.getEntries(), wrapper.getTimesheet());
		TimesheetResponseDto dto = dtoEntityMapper.entityToDto(timesheet);
		GenericWrapperResponse<TimesheetResponseDto> genericDto = GenericWrapperResponse.<TimesheetResponseDto>builder()
				.dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@GetMapping(value = "/read")
	public ResponseEntity<GenericWrapperResponse<TimesheetResponseDto>> readTimesheet(@RequestBody TimesheetMensileKey id) {
		Timesheet timesheet = timesheetService.getTimesheet(id);
		TimesheetResponseDto dto = dtoEntityMapper.entityToDto(timesheet);
		GenericWrapperResponse<TimesheetResponseDto> genericDto = GenericWrapperResponse.<TimesheetResponseDto>builder()
				.dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@DeleteMapping(value = "/delete")
	public ResponseEntity<GenericWrapperResponse<TimesheetResponseDto>> deleteTimesheet(@RequestBody TimesheetMensileKey id) {
		Timesheet timesheet = timesheetService.getTimesheet(id);
		TimesheetResponseDto dto = dtoEntityMapper.entityToDto(timesheet);
		GenericWrapperResponse<TimesheetResponseDto> genericDto = GenericWrapperResponse.<TimesheetResponseDto>builder()
				.dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<GenericWrapperResponse<TimesheetResponseDto>> updateTimesheet(@RequestBody TimesheetWrapper wrapper) {
		Timesheet timesheetEntry = timesheetService.updateTimesheet(wrapper.getEntries(), wrapper.getTimesheet());
		TimesheetResponseDto dto = dtoEntityMapper.entityToDto(timesheetEntry);
		GenericWrapperResponse<TimesheetResponseDto> genericDto = GenericWrapperResponse.<TimesheetResponseDto>builder()
				.dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@PutMapping(value = "/update-status/{status}")
	public ResponseEntity<GenericWrapperResponse<Boolean>> updateTimesheetStatus(@RequestBody TimesheetRefDto timesheetDto, @PathVariable(value = "status") StatoRichiestaType newStatus) {
		TimesheetMensileKey tsKey = new TimesheetMensileKey(timesheetDto.getAnno(), timesheetDto.getMese(), timesheetDto.getCodicePersona());
		Boolean update = timesheetService.editTimesheetStatus(tsKey, newStatus);
		GenericWrapperResponse<Boolean> genericDto = GenericWrapperResponse.<Boolean>builder()
				.dataRichiesta(TSUtils.now()).risultato(update).build();
		if(update) {
			return ResponseEntity.ok(genericDto);
		}
		return ResponseEntity.badRequest().body(genericDto);
	}
	
	@GetMapping(value = "/download-report/{anno}/{mese}/{codicePersona}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> downloadExcelTimesheet(@PathVariable(value = "anno") Integer anno, @PathVariable(value = "mese") EMese mese, @PathVariable(value = "codicePersona") String codicePersona) {
		Utente utente = dipendenteService.readUtenteDipendente(codicePersona);
		InfoAutoDto infoAuto = null;
		if(utente.getAnagrafica().getClass().isAssignableFrom(Dipendente.class)) {
			Dipendente dipendente = (Dipendente) utente.getAnagrafica();
			DatiEconomiciDipendente economics = dipendente.getEconomics();
			infoAuto = new InfoAutoDto(economics.getModelloAuto(), economics.getRimborsoPerKm(), economics.getKmPerGiorno());
		} else if(utente.getAnagrafica().getClass().isAssignableFrom(Consulente.class)) {
			infoAuto = new InfoAutoDto("", 0.0f, 0.0f);
		} else {
			throw new TimesheetException("Tipo utente non valido");
		}

		String fileName = TSUtils.removeAllSpaces(anno + mese.getMonthPart() + "_" + utente.getAnagrafica().getCognome() + TSUtils.EXCEL_EXT).trim();
		AnagraficaDto angraficaDto = dtoEntityMapper.entityToDto(utente.getAnagrafica());
		byte[] excel = timesheetService.downloadExcelTimesheet(anno, mese, angraficaDto, infoAuto);
		return ResponseEntity.ok()
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ fileName + "\"")
	            .body(excel);
	}
}