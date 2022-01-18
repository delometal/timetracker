package com.perigea.tracker.timesheet.service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.perigea.tracker.timesheet.dto.TimesheetDataDto;
import com.perigea.tracker.timesheet.dto.TimesheetInputDto;
import com.perigea.tracker.timesheet.dto.TimesheetResponseDto;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.TimesheetData;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.TimesheetDataKey;
import com.perigea.tracker.timesheet.entity.keys.TimesheetMensileKey;
import com.perigea.tracker.timesheet.exception.FestivitaException;
import com.perigea.tracker.timesheet.exception.TimeSheetException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.CommessaRepository;
import com.perigea.tracker.timesheet.repository.FestivitaRepository;
import com.perigea.tracker.timesheet.repository.TimeSheetDataRepository;
import com.perigea.tracker.timesheet.repository.TimeSheetRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;

@Service
public class TimesheetService {

	@Autowired
	private Logger logger;

	@Autowired
	private TimeSheetDataRepository timesheetRepository;

	@Autowired
	private FestivitaRepository festivitaRepository;

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private CommessaRepository commessaRepo;

	@Autowired
	private TimeSheetRepository mensileRepo;

	public TimesheetResponseDto createTimesheet(List<TimesheetDataDto> list, TimesheetInputDto timeDto) {
		Timesheet timeSheet = DtoEntityMapper.INSTANCE.fromDtoToEntityMensile(timeDto);
		Utente utente = utenteRepository.findByCodicePersona(timeDto.getCodicePersona());
		timeSheet.setUtenteTimesheet(utente);
		utente.addTimesheet(timeSheet);
		TimesheetMensileKey id = new TimesheetMensileKey(timeDto.getAnnoDiRiferimento(), timeDto.getMeseDiRiferimento(),
				timeDto.getCodicePersona());
		timeSheet.setId(id);
//		mensileRepo.save(timeSheet);
		List<TimesheetDataDto> dataList = new ArrayList<TimesheetDataDto>();
		Integer oreTot = 0;
		for (TimesheetDataDto dataDto : list) {
			Commessa commessa = commessaRepo.findByCodiceCommessa(dataDto.getCodiceCommessa());
			TimesheetData data = DtoEntityMapper.INSTANCE.fromDtoToEntityTimeSheet(dataDto);
			TimesheetDataKey dataKey = new TimesheetDataKey(dataDto.getAnnoDiRiferimento(),
					dataDto.getMeseDiRiferimento(), dataDto.getGiornoDiRiferimento(),
					dataDto.getCodicePersona(), dataDto.getCodiceCommessa());
			data.setId(dataKey);
			data.setCommessa(commessa);
			data.setTimeSheet(timeSheet);
			timeSheet.addTimesheet(data);
			TimesheetDataDto dtoData = createTimeSheetData(dataDto);
			oreTot = oreTot + data.getOre();
			dataList.add(dtoData);
		}
		
//		public TimeSheetResponseDto createTimeSheet(List<TimeSheetDataDto> list, TimeSheetInputDto timeDto) {
		//
//				TimeSheetMensileKey id = new TimeSheetMensileKey(2022, 1, "01");
//				Optional<TimeSheet> timesheet = mensileRepo.findById(id);
//				TimeSheet entity = timesheet.get();
//				TimeSheetDataKey dataId = new TimeSheetDataKey(2022,1,3,"01","Ferie");
//				Optional<TimeSheetData> dataTimesheet = timesheetRepository.findById(dataId);
//				TimeSheetData dataEntity = dataTimesheet.get();
//				return null;
		//
//			}
		
		timeSheet.setOreTotali(oreTot);
		mensileRepo.save(timeSheet);
		TimesheetResponseDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoMensile(timeSheet);
		dto.setDataList(dataList);
		return dto;
	}

	public TimesheetDataDto createTimeSheetData(TimesheetDataDto timeSheetParam) {
		try {
//			TimeSheet timeSheet = DtoEntityMapper.INSTANCE.fromDtoToEntityMensile(timeDto);
			Commessa commessa = commessaRepo.findByCodiceCommessa(timeSheetParam.getCodiceCommessa());
			TimesheetData data = DtoEntityMapper.INSTANCE.fromDtoToEntityTimeSheet(timeSheetParam);
			giornoDiRiferimento(timeSheetParam);
			data.setCommessa(commessa);
			
			TimesheetDataKey id = new TimesheetDataKey(timeSheetParam.getAnnoDiRiferimento(),
					timeSheetParam.getMeseDiRiferimento(), timeSheetParam.getGiornoDiRiferimento(),
					timeSheetParam.getCodicePersona(), timeSheetParam.getCodiceCommessa());
			data.setId(id);
//			data.setTimeSheet(timeSheet);
//			timeSheet.addTimeSheet(data);
			timesheetRepository.save(data);
			logger.info("TimeSheet creato e aggiunto a database");
			TimesheetDataDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoTimeSheet(data);
			return dto;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new TimeSheetException("Timesheet non creato " + ex.getMessage());

		}
	}

//
//	public TimeSheetDataDto editTimeSheet(TimeSheetDataDto timeSheetParam, Commessa commessa, Utente utente) {
//		try {
//			TimeSheetData timeSheetEntity=timesheetRepository.findByUtenteTimesheet(utente);
//			if(timeSheetEntity != null) {
//				timesheetRepository.delete(timeSheetEntity);
//				timeSheetEntity = DtoEntityMapper.INSTANCE.fromDtoToEntityTimeSheet(timeSheetParam);
//				timeSheetEntity.setUtenteTimesheet(utente);
//				timeSheetEntity.setCommessaTimesheet(commessa);
//				timesheetRepository.save(timeSheetEntity);
//				logger.info("Timesheet modificato");
//			}
//			TimeSheetDataDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoTimeSheet(timeSheetEntity);
//			return dto;
//		}catch(Exception ex) {
//			throw new EntityNotFoundException("Timesheet non trovato "+ ex.getMessage());	
//		}
//	}
//	
	public void editStatusTimeSheet(TimesheetDataDto timeSheetParam) {
		// if(mapEditUser.containsKey(key)) {
		// TimeSheet
		// timeSheetEntity=timeSheetRepo.findByCodicePersona(timeSheetParam.getCodiceCommessa());
		// if(timeSheetEntity != null) {
		// timeSheetEntity.setStatoType(timeSheetParam.getStatoType().toString());
		// timeSheetRepo.save(timeSheetEntity);
		// } else {
		// LOGGER.info("CodicePersona non trovato");
		// }
		// } else {
		// LOGGER.info("CreateUser non trovato");
		// }
	}

	public void giornoDiRiferimento(TimesheetDataDto timeSheetParam) {
		List<Festivita> festivi = festivitaRepository.findAll();
		LocalDate data = LocalDate.of(timeSheetParam.getAnnoDiRiferimento(), timeSheetParam.getMeseDiRiferimento(),
				timeSheetParam.getGiornoDiRiferimento());
		for (Festivita f : festivi) {
			if (f.getData().isEqual(data) || data.getDayOfWeek() == DayOfWeek.SUNDAY
					|| data.getDayOfWeek() == DayOfWeek.SATURDAY) {
				throw new FestivitaException("Il giorno inserito non è corretto");
			}
			logger.info("Il giorno inserito è corretto");
		}
	}
}