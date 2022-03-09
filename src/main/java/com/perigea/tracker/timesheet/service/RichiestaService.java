package com.perigea.tracker.timesheet.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.dto.ContactDto;
import com.perigea.tracker.commons.dto.HolidayEventRequestDto;
import com.perigea.tracker.commons.dto.NotaSpeseDto;
import com.perigea.tracker.commons.dto.TimesheetEntryDto;
import com.perigea.tracker.commons.dto.TimesheetEventDto;
import com.perigea.tracker.commons.dto.TimesheetRefDto;
import com.perigea.tracker.commons.enums.ApprovalStatus;
import com.perigea.tracker.commons.enums.CalendarEventType;
import com.perigea.tracker.commons.enums.EMese;
import com.perigea.tracker.commons.enums.RichiestaType;
import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.commons.exception.RichiestaException;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.approval.flow.HolidaysApprovalWorkflow;
import com.perigea.tracker.timesheet.approval.flow.TimesheetApprovalWorkflow;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.entity.Richiesta;
import com.perigea.tracker.timesheet.entity.RichiestaHistory;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.TimesheetMensileKey;
import com.perigea.tracker.timesheet.repository.RichiestaHistoryRepository;
import com.perigea.tracker.timesheet.repository.RichiestaRepository;
import com.perigea.tracker.timesheet.repository.TimesheetRepository;

@Service
@Transactional
public class RichiestaService {

	@Autowired
	private Logger logger;

	@Autowired
	private RichiestaRepository richiestaRepository;

	@Autowired
	private RichiestaHistoryRepository richiestaHistoryRepository;

	@Autowired
	private TimesheetService timesheetService;

	@Autowired
	private TimesheetApprovalWorkflow timesheetApprovalWorkflow;

	@Autowired
	private HolidaysApprovalWorkflow holidaysApprovalWorkflow;

	@Autowired
	private ContactDetailsService contactDetailsService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private CommessaService commessaService;
	
	@Autowired
	private TimesheetRepository timesheetRepository;

	public Richiesta createRichiesta(Richiesta richiesta) {
		try {
			richiestaRepository.save(richiesta);
			return richiesta;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new RichiestaException(ex.getMessage());
		}
	}

	public Richiesta readRichiesta(Long codiceRichiesta) {
		try {
			Richiesta richiesta = richiestaRepository.findById(codiceRichiesta).get();
			richiesta.getHistory(); // load history in session
			return richiesta;
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new RichiestaException(ex.getMessage());
		}
	}

	public Richiesta updateRichiesta(Richiesta richiesta) {
		try {
			return richiestaRepository.save(richiesta);
		} catch (Exception ex) {
			throw new RichiestaException(ex.getMessage());
		}
	}

	public void deleteRichiesta(Richiesta richiesta) {
		try {
			richiestaRepository.delete(richiesta);
		} catch (Exception ex) {
			throw new RichiestaException(ex.getMessage());
		}
	}

	public void deleteRichiesta(Long id) {
		try {
			richiestaRepository.deleteById(id);
		} catch (Exception ex) {
			throw new RichiestaException(ex.getMessage());
		}
	}

	public Richiesta updateRichiestaHistory(RichiestaHistory history) {
		try {
			Richiesta richiesta = richiestaRepository.findById(history.getRichiesta().getCodiceRichiesta()).get();
			RichiestaHistory historyOld = null;
			for (RichiestaHistory h : richiesta.getHistory()) {
				if (h.getCodiceRichiestaHistory() == history.getCodiceRichiestaHistory()) {
					historyOld = h;
					break;
				}
			}
			if (historyOld != null) {
				richiesta.getHistory().remove(historyOld);
			}
			richiesta.addRichiestaHistory(history);
			return richiestaRepository.save(richiesta);
		} catch (Exception ex) {
			throw new RichiestaException(ex.getMessage());
		}
	}

	public Richiesta deleteRichiestaHistory(RichiestaHistory history) {
		try {
			Richiesta richiesta = richiestaRepository.findById(history.getRichiesta().getCodiceRichiesta()).get();
			RichiestaHistory historyOld = null;
			for (RichiestaHistory h : richiesta.getHistory()) {
				if (h.getCodiceRichiestaHistory() == history.getCodiceRichiestaHistory()) {
					historyOld = h;
					break;
				}
			}
			if (historyOld != null) {
				richiesta.getHistory().remove(historyOld);
			}
			return richiestaRepository.save(richiesta);
		} catch (Exception ex) {
			throw new RichiestaException(ex.getMessage());
		}
	}

	public Richiesta sendRichiestaTimesheet(TimesheetRefDto timesheetReferences) {
		try {
			TimesheetMensileKey key = new TimesheetMensileKey(timesheetReferences.getAnno(),
					timesheetReferences.getMese(), timesheetReferences.getCodicePersona());
			Timesheet timesheet = timesheetService.getTimesheet(key);
			Richiesta richiesta = timesheet.getRichiesta();
			RichiestaHistory history = RichiestaHistory.builder()
					.responsabile(timesheet.getPersonale().getResponsabile()).stato(ApprovalStatus.PENDING)
					.richiesta(richiesta).build();
			ContactDto richiestaCreator = contactDetailsService
					.readUserContactDetails(richiesta.getRichiedente().getCodicePersona());
			ContactDto responsabile = contactDetailsService
					.readUserContactDetails(history.getResponsabile().getCodicePersona());
			TimesheetEventDto timesheetEvent = TimesheetEventDto.builder().id(Utils.uuid())
					.eventCreator(richiestaCreator).responsabile(responsabile)
					.approvalStatus(timesheet.getStatoRichiesta()).type(CalendarEventType.Timesheet)
					.timesheet(timesheetReferences).startDate(new Date()).endDate(new Date()).build();
			timesheetApprovalWorkflow.richiestaTimesheet(timesheetEvent, richiesta, history);
			return richiesta;
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new RichiestaException(ex.getMessage());
		}
	}

	public Richiesta sendHolidaysRequest(HolidayEventRequestDto event) {
		try {
			Utente eventCreator = utenteService.readUtente(event.getEventCreator().getCodicePersona());
			Utente responsabile = utenteService.readUtente(event.getResponsabile().getCodicePersona());
			Richiesta richiesta = Richiesta.builder().richiedente(eventCreator).tipo(RichiestaType.FERIE_PERMESSI)
					.build();
			RichiestaHistory history = RichiestaHistory.builder().responsabile(responsabile.getPersonale())
					.stato(ApprovalStatus.PENDING).richiesta(richiesta).build();
			history.setRichiesta(richiesta);
			richiesta.addRichiestaHistory(history);
			createRichiesta(richiesta);
			holidaysApprovalWorkflow.holidaysRequest(event, richiesta, history);
			return richiesta;
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new RichiestaException(ex.getMessage());
		}
	}

	public Richiesta approveHolidaysRequest(HolidayEventRequestDto event, Long historyId, ApprovalStatus newStatus) {
		RichiestaHistory history = richiestaHistoryRepository.findById(historyId).get();
		history.setStato(newStatus);
		Richiesta richiesta = updateRichiestaHistory(history);

//		if (newStatus.equals(ApprovalStatus.APPROVED)) {
//			LocalDateTime startDate = Utils.convertToLocalDateTimeViaInstant(event.getStartDate());
//			LocalDateTime endDate = Utils.convertToLocalDateTimeViaInstant(event.getEndDate());
//
//			CommessaNonFatturabile commessa = commessaService
//					.saveCommessaNonFatturabile(new CommessaNonFatturabile(event.getType().name()));
//
//			TimesheetRefDto timesheetRef = new TimesheetRefDto(richiesta.getRichiedente().getCodicePersona(),
//					endDate.getYear(), endDate.getMonthValue());
//			
//			if (endDate.getMonthValue() == startDate.getMonthValue()) {
//				getTimesheet(startDate, endDate, commessa, timesheetRef);
//			} else {
//				Integer monthEndDay = EMese.getDays(startDate.getMonthValue(), startDate.getYear());
//				LocalDateTime monthEndDate = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
//						monthEndDay, 18, 0);
//				getTimesheet(startDate, monthEndDate, commessa, timesheetRef);
//				LocalDateTime monthStartDate = LocalDateTime.of(endDate.getYear(), endDate.getMonthValue(), 1, 9, 0);
//				getTimesheet(monthStartDate, endDate, commessa, timesheetRef);
//			}
//
//		}
		holidaysApprovalWorkflow.approveHolidaysRequest(event, richiesta, history);
		return richiesta;
	}
	
	

//	public Integer checkOre(LocalDateTime startDate, LocalDateTime endDate) {
//		if (endDate.getDayOfMonth() == startDate.getDayOfMonth()) {
//			return endDate.getHour() - startDate.getHour();
//		} else
//			return 8;
//	}
//	
//	
//
//	public Timesheet getTimesheet(LocalDateTime startDate, LocalDateTime endDate, CommessaNonFatturabile commessa,
//			TimesheetRefDto ref) {
//		List<TimesheetEntryDto> entries = new ArrayList<TimesheetEntryDto>();
//		Integer ore = checkOre(startDate, endDate);
//		Integer giorni = endDate.getDayOfMonth() - startDate.getDayOfMonth();
//		Integer i = 0;
//		do {
//			TimesheetEntryDto entry = TimesheetEntryDto.builder().codiceCommessa(commessa.getCodiceCommessa())
//					.giorno(startDate.getDayOfMonth() + i)
//					.descrizioneCommessa(commessa.getDescrizioneCommessa())
//					.ore(ore)
//					.tipoCommessa(commessa.getTipoCommessa())
//					.noteSpesa(new ArrayList<NotaSpeseDto>())
//					.ragioneSociale(commessa.getCliente().getRagioneSociale())
//					.build();
//			entries.add(entry);
//			i++;
//		} while (i < giorni);
//
//		Timesheet timesheet;
//		try {
//			timesheet = timesheetService.getTimesheet(new TimesheetMensileKey(ref.getAnno(), ref.getMese(), ref.getCodicePersona()));
//			timesheet = timesheetService.updateTimesheet(entries, ref);
//		} catch (EntityNotFoundException e) {
//			logger.info("il timesheet non esiste ancora");
//			timesheet = timesheetService.createTimesheet(entries, ref);
//		}
//		return timesheet;
//	}

}