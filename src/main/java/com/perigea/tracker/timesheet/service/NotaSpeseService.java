package com.perigea.tracker.timesheet.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.NotaSpeseDto;
import com.perigea.tracker.timesheet.entity.NotaSpese;
import com.perigea.tracker.timesheet.entity.TimesheetEntry;
import com.perigea.tracker.timesheet.entity.keys.TimesheetEntryKey;
import com.perigea.tracker.timesheet.exception.NotaSpeseException;
import com.perigea.tracker.timesheet.repository.NotaSpeseRepository;
import com.perigea.tracker.timesheet.repository.TimesheetDataRepository;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@Service
public class NotaSpeseService {
	
	@Autowired
	private NotaSpeseRepository notaSpeseRepository;
	
	@Autowired
	private TimesheetDataRepository timesheetEntryRepository;
	
	@Autowired
	private Logger logger;

	
	public NotaSpese createNotaSpese(NotaSpeseDto notaSpeseDto, TimesheetEntryKey id) {
		try {
			TimesheetEntry timesheet = timesheetEntryRepository.findById(id);
			NotaSpese notaSpese = DtoEntityMapper.INSTANCE.fromDtoToEntityNotaSpese(notaSpeseDto);
			if(timesheet != null) {
				timesheet.addNotaSpese(notaSpese);	
				notaSpeseRepository.save(notaSpese);
			}
			return notaSpese;
		}catch(Exception ex) {
			throw new NotaSpeseException("NotaSpese non creata");
		}
	}
	
	public List<NotaSpese> readNotaSpese(String codicePersona){
		try {
			List<NotaSpese> allNotaSpeseList= notaSpeseRepository.findAll();
			List<NotaSpese> notaSpeseList = new ArrayList<>();
			for(NotaSpese entity: allNotaSpeseList) {
				if(entity.getUtente().getCodicePersona().equalsIgnoreCase(codicePersona)) {
					notaSpeseList.add(entity);
				}
			}
			return notaSpeseList;
		} catch (Exception ex) {
			throw new NotaSpeseException("Note Spese non trovate");
		}
	}
	
	public NotaSpese updateNotaSpese(NotaSpeseDto notaSpeseDto) {
		try {
			NotaSpese notaSpesa =  notaSpeseRepository.findById(DtoEntityMapper.INSTANCE.fromDtoToEntityNotaSpese(notaSpeseDto).getId());
			if(notaSpesa != null) {
				notaSpeseRepository.save(notaSpesa);
			}
			return notaSpesa;
		}catch(Exception ex) {
			throw new NotaSpeseException("NotaSpese non trovata");
		}
	}
	
	public NotaSpese deleteNotaSpese(String codicePersona, String codiceCommessa) {
		try {
			List<NotaSpese> notaSpeseList= notaSpeseRepository.findAll();
			for(NotaSpese entity: notaSpeseList) {
				if(entity.getUtente().getCodicePersona().equalsIgnoreCase(codicePersona) && entity.getCommessa().getCodiceCommessa().equalsIgnoreCase(codiceCommessa) ) {
					notaSpeseRepository.delete(entity);
					return entity;
				}
			}
			return null;
		}catch(Exception ex) {
			throw new NotaSpeseException("NotaSpese non trovata");
		}
	}
}
