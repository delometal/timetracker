package com.perigea.tracker.timesheet.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.FestivitaDto;
import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.exception.FestivitaException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.FestivitaRepository;

@Service
public class FestivitaService {

	@Autowired
	private FestivitaRepository festivitaRepository;

	@Autowired
	private Logger logger;

	public FestivitaDto createFestivita(FestivitaDto festivitaDto) {
		try {
			Festivita entity = DtoEntityMapper.INSTANCE.FromDtoToEntityFestivita(festivitaDto);
			festivitaRepository.save(entity);
			logger.info("festività inserita con successo a db");
			FestivitaDto dto = DtoEntityMapper.INSTANCE.FromEntityToDtoFestivita(entity);
			return dto;
		} catch (Exception ex) {
			throw new FestivitaException("festività non inserita");
		}
	}

	public FestivitaDto updateFestivita(FestivitaDto festivitaDto) {
		try {
			Festivita entity = festivitaRepository.findByNomeFestivo(festivitaDto.getNomeFestivo());
			if (entity != null) {
				entity = DtoEntityMapper.INSTANCE.FromDtoToEntityFestivita(festivitaDto);
				festivitaRepository.save(entity);
			}
			FestivitaDto dto = DtoEntityMapper.INSTANCE.FromEntityToDtoFestivita(entity);
			return dto;
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
