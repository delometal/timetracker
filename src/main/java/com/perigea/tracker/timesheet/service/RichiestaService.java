package com.perigea.tracker.timesheet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.dto.ContactDto;
import com.perigea.tracker.commons.dto.HolidayEventDto;
import com.perigea.tracker.commons.dto.HolidayEventRequestDto;
import com.perigea.tracker.commons.dto.NotaSpeseDto;
import com.perigea.tracker.commons.dto.TimesheetEntryDto;
import com.perigea.tracker.commons.dto.TimesheetEventDto;
import com.perigea.tracker.commons.dto.TimesheetRefDto;
import com.perigea.tracker.commons.enums.ApprovalStatus;
import com.perigea.tracker.commons.enums.CalendarEventType;
import com.perigea.tracker.commons.enums.CommessaType;
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
import com.perigea.tracker.timesheet.entity.TimesheetEntry;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.TimesheetMensileKey;
import com.perigea.tracker.timesheet.repository.RichiestaHistoryRepository;
import com.perigea.tracker.timesheet.repository.RichiestaRepository;

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
	
	
	/**
	 * invia una richiesta per l'approvazione del timesheet al responsabile
	 * @param timesheetReferences
	 * @return
	 */
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
					.timesheet(timesheetReferences).build();
			timesheetApprovalWorkflow.richiestaTimesheet(timesheetEvent, richiesta, history);
			return richiesta;
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new RichiestaException(ex.getMessage());
		}
	}
	
	
	/**
	 * invia una richiesta per ferie/permessi al responsabile
	 * @param event
	 * @return
	 */
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
	
	
	/**
	 * approvazione di tutte le Ferie/permessi attraverso lo status complessivo e
	 * invio notifica al richiedente ed implementazione automatica del timesheet
	 * 
	 * @param event
	 * @param historyId
	 * @param newStatus
	 * @return
	 */
	public Richiesta approveHolidaysRequest(HolidayEventRequestDto event, Long historyId, ApprovalStatus newStatus) {
		RichiestaHistory history = richiestaHistoryRepository.findById(historyId).get();
		history.setStato(newStatus);
		Richiesta richiesta = updateRichiestaHistory(history);

		if (newStatus.equals(ApprovalStatus.APPROVED)) {
			List<TimesheetEntryDto> entries = new ArrayList<TimesheetEntryDto>();
		
			TimesheetRefDto ref = null;

			for (HolidayEventDto e : event.getHolidays()) {
				CommessaNonFatturabile commessa = commessaService
						.saveCommessaNonFatturabile(new CommessaNonFatturabile(e.getTipo().name()));
				if (ref==null) {
					ref = new TimesheetRefDto(richiesta.getRichiedente().getCodicePersona(), e.getData().getYear(),
							e.getData().getMonthValue());
				}
				if (ref.getMese()!=e.getData().getMonthValue()) {
					timesheetService.createTimesheet(entries, ref);
					entries.clear();
					ref = new TimesheetRefDto(richiesta.getRichiedente().getCodicePersona(), e.getData().getYear(),
							e.getData().getMonthValue());
				}

				TimesheetEntryDto entry = TimesheetEntryDto.builder().codiceCommessa(commessa.getCodiceCommessa())
						.giorno(e.getData().getDayOfMonth())
						.descrizioneCommessa(commessa.getDescrizioneCommessa())
						.ore(e.getOre())
						.tipoCommessa(commessa.getTipoCommessa())
						.noteSpesa(new ArrayList<NotaSpeseDto>())
						.ragioneSociale(commessa.getCliente().getRagioneSociale())
						.build();
				entries.add(entry);
				
			}
			timesheetService.createTimesheet(entries, ref);
		
		}
		holidaysApprovalWorkflow.approveAllHolidaysRequest(event, richiesta, history);
		return richiesta;
	}
	
	
	/**
	 * approvazione delle Ferie/permessi attraverso i singoli eventi e invio
	 * notifica al riciedente con eventuale elenco per gli eventi declinati ed
	 * implementazione automatica del timesheet
	 * 
	 * @param event
	 * @param historyId
	 * @return
	 */
	public Richiesta approveHolidaysRequest(HolidayEventRequestDto event, Long historyId) {
		RichiestaHistory history = richiestaHistoryRepository.findById(historyId).get();
		history.setStato(event.getApproved());
		Richiesta richiesta = updateRichiestaHistory(history);

		List<TimesheetEntryDto> entries = new ArrayList<TimesheetEntryDto>();

		TimesheetRefDto ref = null;

		for (HolidayEventDto e : event.getHolidays()) {
			if (e.getStatus().equals(ApprovalStatus.APPROVED)) {

				CommessaNonFatturabile commessa = commessaService
						.saveCommessaNonFatturabile(new CommessaNonFatturabile(e.getTipo().name()));
				if (ref == null) {
					ref = new TimesheetRefDto(richiesta.getRichiedente().getCodicePersona(), e.getData().getYear(),
							e.getData().getMonthValue());
				}
				if (ref.getMese() != e.getData().getMonthValue()) {
					timesheetService.createTimesheet(entries, ref);
					entries.clear();
					ref = new TimesheetRefDto(richiesta.getRichiedente().getCodicePersona(), e.getData().getYear(),
							e.getData().getMonthValue());
				}

				TimesheetEntryDto entry = TimesheetEntryDto.builder().codiceCommessa(commessa.getCodiceCommessa())
						.giorno(e.getData().getDayOfMonth())
						.descrizioneCommessa(commessa.getDescrizioneCommessa())
						.ore(e.getOre())
						.tipoCommessa(commessa.getTipoCommessa())
						.noteSpesa(new ArrayList<NotaSpeseDto>())
						.ragioneSociale(commessa.getCliente().getRagioneSociale())
						.build();
				entries.add(entry);

			}

		}
		timesheetService.createTimesheet(entries, ref);
		holidaysApprovalWorkflow.approveSingleHolidaysRequest(event, richiesta, history);
		return richiesta;
	}

	/**
	 * richiesta di annullamento da parte del richiedente di ferie/permessi con
	 * notifica al responsabile
	 * 
	 * @param event
	 * @return
	 */
	public Richiesta cancelHolidays(HolidayEventRequestDto event) {
		Utente eventCreator = utenteService.readUtente(event.getEventCreator().getCodicePersona());
		Utente responsabile = utenteService.readUtente(event.getResponsabile().getCodicePersona());
		Richiesta richiesta = Richiesta.builder().richiedente(eventCreator).tipo(RichiestaType.FERIE_PERMESSI).build();
		RichiestaHistory history = RichiestaHistory.builder().responsabile(responsabile.getPersonale())
				.stato(ApprovalStatus.PENDING).richiesta(richiesta).build();
		history.setRichiesta(richiesta);
		richiesta.addRichiestaHistory(history);
		createRichiesta(richiesta);
		
		holidaysApprovalWorkflow.cancelHolidays(event, richiesta, history);
		return richiesta;
	}
	
	
	/**
	 * approvazione dell'annullamento delle ferie/permessi on notifica al
	 * richiedente ed elimiazione automatica del timesheet
	 * 
	 * @param event
	 * @param historyId
	 * @return
	 */
	public Richiesta approveCancelHolidays(HolidayEventRequestDto event, Long historyId) {
		RichiestaHistory history = richiestaHistoryRepository.findById(historyId).get();
		history.setStato(event.getApproved());
		Richiesta richiesta = updateRichiestaHistory(history);

		for (HolidayEventDto e : event.getHolidays()) {

			List<TimesheetEntry> entries = timesheetService.findByQueryNative(e.getData().getDayOfMonth(),
					e.getData().getMonthValue(), e.getData().getYear(), richiesta.getRichiedente().getCodicePersona());
			for (TimesheetEntry entry : entries) {
				if (entry.getCommessa().getTipoCommessa().equals(CommessaType.S)) {
					timesheetService.deleteTimesheetEntry(entry);
				}
			}
		}

		holidaysApprovalWorkflow.approveCancelHolidays(event, richiesta, history);
		return richiesta;
	
	}
}