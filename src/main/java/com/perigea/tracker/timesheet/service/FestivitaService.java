package com.perigea.tracker.timesheet.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.FestivitaDto;
import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.exception.FestivitaException;
import com.perigea.tracker.timesheet.repository.FestivitaRepository;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;





@Service
public class FestivitaService {

	@Autowired
	private FestivitaRepository festivitaRepository;

	@Autowired
	private Logger logger;

	public Festivita createFestivita(FestivitaDto festivitaDto) {
		try {
			Festivita festivita = DtoEntityMapper.INSTANCE.FromDtoToEntityFestivita(festivitaDto);
			festivitaRepository.save(festivita);
			logger.info("festività inserita con successo a db");
//			FestivitaDto dto = DtoEntityMapper.INSTANCE.FromEntityToDtoFestivita(entity);
//			return dto;
			return festivita;
		} catch (Exception ex) {
			throw new FestivitaException("festività non inserita");
		}
	}

	public Festivita updateFestivita(FestivitaDto festivitaDto) {
		try {
			Festivita festivita = festivitaRepository.findByNomeFestivo(festivitaDto.getNomeFestivo());
			if (festivita != null) {
				festivita = DtoEntityMapper.INSTANCE.FromDtoToEntityFestivita(festivitaDto);
				festivitaRepository.save(festivita);
			}
//			FestivitaDto dto = DtoEntityMapper.INSTANCE.FromEntityToDtoFestivita(entity);
//			return dto;
			return festivita;
		} catch (Exception ex) {
			throw new FestivitaException("festività non trovata");
		}
	}

//	private void getEasterDate(LocalDate easter) {
//		Festivita pasqua = new Festivita();
//		pasqua.setData(easter);
//		pasqua.setNomeFestivo("Pasqua");
//		festiviRepo.save(pasqua);
//	}
//		
//	private void getEasterMondayDate(LocalDate easterMonday) {
//		Festivita pasquetta = new Festivita();
//		pasquetta.setData(easterMonday);
//		pasquetta.setNomeFestivo("Pasquetta");
//		festiviRepo.save(pasquetta);
//	}
}
