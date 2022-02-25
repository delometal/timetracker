package com.perigea.tracker.timesheet.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.perigea.tracker.commons.dto.CentroDiCostoDto;
import com.perigea.tracker.commons.dto.ClienteDto;
import com.perigea.tracker.commons.dto.CommessaDto;
import com.perigea.tracker.commons.dto.CommessaFatturabileDto;
import com.perigea.tracker.commons.dto.CommessaNonFatturabileDto;
import com.perigea.tracker.commons.dto.ConsulenteDto;
import com.perigea.tracker.commons.dto.ContactDto;
import com.perigea.tracker.commons.dto.DatiEconomiciConsulenteDto;
import com.perigea.tracker.commons.dto.DatiEconomiciDipendenteDto;
import com.perigea.tracker.commons.dto.DipendenteCommessaDto;
import com.perigea.tracker.commons.dto.DipendenteDto;
import com.perigea.tracker.commons.dto.FestivitaDto;
import com.perigea.tracker.commons.dto.FornitoreDto;
import com.perigea.tracker.commons.dto.GruppoContattoDto;
import com.perigea.tracker.commons.dto.NotaSpeseDto;
import com.perigea.tracker.commons.dto.NotaSpeseInputDto;
import com.perigea.tracker.commons.dto.OrdineCommessaDto;
import com.perigea.tracker.commons.dto.RichiestaDto;
import com.perigea.tracker.commons.dto.RichiestaHistoryDto;
import com.perigea.tracker.commons.dto.RuoloDto;
import com.perigea.tracker.commons.dto.TimesheetEntryDto;
import com.perigea.tracker.commons.dto.TimesheetRefDto;
import com.perigea.tracker.commons.dto.TimesheetResponseDto;
import com.perigea.tracker.commons.dto.UtenteDto;
import com.perigea.tracker.timesheet.entity.CentroDiCosto;
import com.perigea.tracker.timesheet.entity.Cliente;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.CommessaFatturabile;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.entity.Consulente;
import com.perigea.tracker.timesheet.entity.DatiEconomiciConsulente;
import com.perigea.tracker.timesheet.entity.DatiEconomiciDipendente;
import com.perigea.tracker.timesheet.entity.Dipendente;
import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.entity.Fornitore;
import com.perigea.tracker.timesheet.entity.Gruppo;
import com.perigea.tracker.timesheet.entity.NotaSpese;
import com.perigea.tracker.timesheet.entity.OrdineCommessa;
import com.perigea.tracker.timesheet.entity.PersonaleCommessa;
import com.perigea.tracker.timesheet.entity.Richiesta;
import com.perigea.tracker.timesheet.entity.RichiestaHistory;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.TimesheetEntry;
import com.perigea.tracker.timesheet.entity.Utente;

@Mapper(componentModel = "spring")
public interface DtoEntityMapper {
	
	
	/** UTENTE & RUOLI  **/
	Utente dtoToEntity(UtenteDto dto);

	@Mapping(ignore = true, target = "password")
	UtenteDto entityToDto(Utente entity);
		
	Ruolo dtoToEntity(RuoloDto dto);
	RuoloDto entityToDto(Ruolo entity);
	
	List<RuoloDto> entityToDtoRuoloList(List<Ruolo> list);
	List<Ruolo> dtoToEntityRuoloList(List<RuoloDto> list);
	
	List<Utente> dtoToEntityUtenteList(List<UtenteDto> dto);
	List<UtenteDto> entityToDtoUtenteList(List<Utente> entity);
	
	

	
	/** PERSONALE - DIPENDENTI & CONSULENTI **/
	@Mapping(source = "codiceResponsabile", target = "responsabile.codicePersona")
	Dipendente dtoToEntity(DipendenteDto dto);
	@Mapping(source = "responsabile.codicePersona", target = "codiceResponsabile")
	DipendenteDto entityToDto(Dipendente entity);
		
	Consulente dtoToEntity(ConsulenteDto dto);
	ConsulenteDto entityToDto(Consulente entity);
	
	
	/** CLIENTI & FORNITORI **/
	Cliente dtoToEntity(ClienteDto dto);
	ClienteDto entityToDto(Cliente entity);
	List<Cliente> dtoToEntityClienteList(List<ClienteDto> dto);
	List<ClienteDto> entityToDtoClienteList(List<Cliente> entity);
	
	Fornitore dtoToEntity(FornitoreDto dto);
	FornitoreDto entityToDto(Fornitore entity);
	List<Fornitore> dtoToEntityFornitoreList(List<FornitoreDto> dto);
	List<FornitoreDto> entityToDtoFornitoreList(List<Fornitore> entity);

	
	/** NOTE SPESE **/
	@Mapping(target= "id.costoNotaSpese", source="costoNotaSpese")
	NotaSpese dtoToEntity(NotaSpeseDto dto);
	
	@Mapping(target= "id.anno", source="anno")
	@Mapping(target= "id.mese", source="mese")
	@Mapping(target= "id.costoNotaSpese", source="costoNotaSpese")
	NotaSpese dtoToEntity(NotaSpeseInputDto dto);
	
	@Mapping(target= ".", source="id")
	NotaSpeseDto entityToDto(NotaSpese entity);
	
	@Mapping(target= "id.giorno", source="giorno")
	@Mapping(target= "id.codicePersona", source="codicePersona")
	@Mapping(target= "id.codiceCommessa", source="ccodiceCommessa")
	List<NotaSpese> dtoToEntityNotaSpeseList(List<NotaSpeseDto> dto);
	List<NotaSpeseDto> entityToDtoNotaSpeseList(List<NotaSpese> entity);

	
	/** ORDINI & COMMESSE **/
	Commessa dtoToEntity(CommessaDto dto);
	CommessaDto entityToDto(Commessa entity);
	
	@Mapping(target= "codiceCommessa", source= "commessa.codiceCommessa")
	@Mapping(target= "tipoCommessa", source= "commessa.tipoCommessa")
	@Mapping(target= "descrizioneCommessa", source= "commessa.descrizioneCommessa")
	CommessaFatturabile dtoToEntity(CommessaFatturabileDto dto);
	
	@Mapping(target= "commessa.codiceCommessa", source="codiceCommessa")
	@Mapping(target= "commessa.tipoCommessa", source="tipoCommessa")
	@Mapping(target= "commessa.descrizioneCommessa", source= "descrizioneCommessa")
	CommessaFatturabileDto entityToDto(CommessaFatturabile entity);
	
	@Mapping(target= "codiceCommessa", source= "commessa.codiceCommessa")
	@Mapping(target= "tipoCommessa", source= "commessa.tipoCommessa")
	@Mapping(target= "descrizioneCommessa", source= "commessa.descrizioneCommessa")
	CommessaNonFatturabile dtoToEntity(CommessaNonFatturabileDto dto);
	
	@Mapping(target= "commessa.codiceCommessa", source="codiceCommessa")
	@Mapping(target= "commessa.tipoCommessa", source="tipoCommessa")
	@Mapping(target= "commessa.descrizioneCommessa", source= "descrizioneCommessa")
	CommessaNonFatturabileDto entityToDto(CommessaNonFatturabile entity);
	
	@Mapping(target= "id.codiceCommessa", source="codiceCommessa")
	@Mapping(target= "id.numeroOrdineCliente", source="numeroOrdineCliente")
	@Mapping(target= "id.codiceAzienda", source="codiceAzienda")
	OrdineCommessa dtoToEntity(OrdineCommessaDto dto);
	
	@Mapping(target= ".", source="id")
	OrdineCommessaDto entityToDto(OrdineCommessa entity);
	
	@Mapping(target= "id.codicePersona", source="codicePersona")
	@Mapping(target= "id.codiceCommessa", source="codiceCommessa")
	PersonaleCommessa dtoToEntity(DipendenteCommessaDto dto);
	
	@Mapping(target= ".", source="id")
	DipendenteCommessaDto entityToDto(PersonaleCommessa entity);

	
	/** GRUPPI E CONTATTI **/
	Gruppo dtoToEntity(GruppoContattoDto gruppo);
	GruppoContattoDto entityToDto(Gruppo gruppo);

	ContactDto entityToContactDto(Utente entity);
	List<ContactDto> entityToContactDtoList(List<Utente> entity);
	
	
	/** DATI ECONOMICI **/
	DatiEconomiciDipendenteDto entityToDto(DatiEconomiciDipendente entity);
	DatiEconomiciDipendente dtoToEntity(DatiEconomiciDipendenteDto dto);
	
	DatiEconomiciConsulenteDto entityToDto(DatiEconomiciConsulente entity);
	DatiEconomiciConsulente dtoToEntity(DatiEconomiciConsulenteDto dto);
	
	
	/** TIMESHEET **/
	@Mapping(target= "id.anno", source="anno")
	@Mapping(target= "id.mese", source="mese")
	@Mapping(target= "id.codicePersona", source="codicePersona")
	Timesheet dtoToEntity(TimesheetRefDto dto);
	
	@Mapping(target = "id.giorno", source = "giorno")
	@Mapping(target = "id.codiceCommessa", source = "codiceCommessa")
	@Mapping(target = "commessa.descrizioneCommessa", source = "descrizioneCommessa")
	@Mapping(target = "commessa.cliente.ragioneSociale", source = "ragioneSociale")
	TimesheetEntry dtoToEntity(TimesheetEntryDto dto);
	
	@Mapping(target= "giorno", source="id.giorno")
	@Mapping(target= "codiceCommessa", source="id.codiceCommessa")
	@Mapping (target = "descrizioneCommessa", source = "commessa.descrizioneCommessa")
	@Mapping (target = "ragioneSociale", source = "commessa.cliente.ragioneSociale")
	TimesheetEntryDto entityToDto(TimesheetEntry entity);
	
	List<TimesheetEntry> fromEntityToDtoTimesheetData(List<TimesheetEntryDto> dtos);
	List<TimesheetEntryDto> entityToDtoTimesheetEntryList(List<TimesheetEntry> entities);

	@Mapping(target= ".", source="id")
	@Mapping(target= "nome", source="personale.utente.nome")
	@Mapping(target= "cognome", source="personale.utente.cognome")
	@Mapping (target = "mailAziendale", source = "personale.utente.mailAziendale")
	TimesheetResponseDto entityToDto(Timesheet entity);
	

	/** RICHIESTE **/
	@Mapping(target = "codiceOwner", source = "richiedente.codicePersona")
	RichiestaDto entityToDto(Richiesta entity);
	
	@Mapping(target = "richiedente.codicePersona", source = "codiceOwner")
	Richiesta dtoToEntity(RichiestaDto dto);
	
	@Mapping(target = "codiceResponsabile", source = "responsabile.codicePersona")
	RichiestaHistoryDto entityToDto(RichiestaHistory entity);
	
	@Mapping(target = "responsabile.codicePersona", source = "codiceResponsabile")
	RichiestaHistory dtoToEntity(RichiestaHistoryDto dto);
	
	List<RichiestaHistoryDto> entityToDtoRichiestaHistoryList(List<RichiestaHistory> entities);
	List<RichiestaHistory> dtoToEntityRichiestaHistoryList(List<RichiestaHistoryDto> dtos);
	
	
	/** FESTIVITA **/
	Festivita dtoToEntity(FestivitaDto dto);
	FestivitaDto entityToDto(Festivita entity);

	
	/** CENTRI DI COSTO **/
	CentroDiCosto dtoToEntity(CentroDiCostoDto dto);
	CentroDiCostoDto entityToDto(CentroDiCosto entity);
	List<CentroDiCosto> dtoToEntityCentroDiCostoList(List<CentroDiCostoDto> dto);
	List<CentroDiCostoDto> entityToDtoCentroDiCostoList(List<CentroDiCosto> entity);

}