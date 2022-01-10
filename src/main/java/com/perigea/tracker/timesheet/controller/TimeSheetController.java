package com.perigea.tracker.timesheet.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.TimeSheetDto;
import com.perigea.tracker.timesheet.service.TimeSheetService;
import com.perigea.tracker.timesheet.wrapper.TimeSheetWrapper;


@RestController
public class TimeSheetController {
	

	@Autowired
	private TimeSheetService timeSheetService;
	

	// Metodo per creare un timesheet
	@PostMapping(value = "/create-timesheet")
	public ResponseEntity <?> createTimeSheet(@RequestBody TimeSheetWrapper bodyConverter, @RequestParam String nomeModifica, @RequestParam String cognomeModifica) {
		TimeSheetDto dto=timeSheetService.createTimeSheet(bodyConverter.getUtente(),bodyConverter.getCommessa(),bodyConverter.getTimeDto());
		Date date=new Date();
		GenericWrapperResponse<TimeSheetDto> genericDto=GenericWrapperResponse.<TimeSheetDto>builder()
				.dataRichiesta(date)
				.utenteModifica(nomeModifica+cognomeModifica)
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

}
