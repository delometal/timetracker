package com.perigea.tracker.timesheet.mapper;

import java.util.List;

import com.perigea.tracker.commons.dto.*;
import com.perigea.tracker.timesheet.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DtoEntityMapper {
	
	
	/** UTENTE & RUOLI  **/
	Utente dtoToEntity(UtenteDto dto);

	@Mapping(ignore = true, target = "password")
	@Mapping(target = "createUser", ignore = true)
	@Mapping(target = "createTimestamp", ignore = true)
	@Mapping(target = "lastUpdateUser", ignore = true)
	@Mapping(target = "lastUpdateTimestamp", ignore = true)
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
	List<ConsulenteDto> entityToDtoConsulenteList(List<Consulente> dto);
	List<Consulente> dtoToEntityConsulenteList(List<ConsulenteDto> entity);
	
	
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
	@Mapping(target = "id.giorno", source = "giorno")
	@Mapping(target = "id.codicePersona", source = "codicePersona")
	@Mapping(target = "id.codiceCommessa", source = "codiceCommessa")
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
	
	List<DipendenteCommessaDto> entityToDtoListDipendenteCommessa(List<PersonaleCommessa> entity);

	@Mapping(target = "id.codiceCommessa", source = "codiceCommessa")
	@Mapping(target = "id.dataEstensione", source = "dataEstensione")
	CommessaEstensione dtoToEntity(CommessaEstensioneDto dto);
	
	@Mapping(target = "codiceCommessa", source = "id.codiceCommessa")
	@Mapping(target = "dataEstensione", source = "id.dataEstensione")
	CommessaEstensioneDto entityToDto(CommessaEstensione entity);
	
	List<CommessaEstensione> dtoToEntityList(List<CommessaEstensioneDto> dto);
	List<CommessaEstensioneDto> entityToDtoList(List<CommessaEstensione> entity);
	
	List<CommessaFatturabileDto> entityToCommessaFattDtoList(List<CommessaFatturabile> entity);
	List<CommessaNonFatturabileDto> entityToCommessaNoFattDtoList(List<CommessaNonFatturabile> entity);
	List<CommessaDto> entityToCommessaDtoList(List<Commessa> entity);

	
	/** GRUPPI E CONTATTI **/
	Gruppo dtoToEntity(GruppoContattoDto gruppo);
	GruppoContattoDto entityToDto(Gruppo gruppo);

	ContactDto entityToContactDto(Utente entity);
	List<ContactDto> entityToContactDtoList(List<Utente> entity);
	

	
	
	List<DipendenteDto> entityToDipendenteDtoList(List<Dipendente> entity);
	
	
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
	
	List<TimesheetResponseDto> entityToDto(List<Timesheet> entity);

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

	/** AVVISI BACHECA **/
	AvvisoBacheca dtoToEntity(AvvisoBachecaDto dto);
	@Mapping(target = "recipients", ignore = true)
	@Mapping(target = "createUser", ignore = true)
	@Mapping(target = "createTimestamp", ignore = true)
	@Mapping(target = "lastUpdateUser", ignore = true)
	@Mapping(target = "lastUpdateTimestamp", ignore = true)
	AvvisoBachecaDto entityToDto(AvvisoBacheca entity);
	List<AvvisoBachecaDto> avvisoEntityToDtoList(List<AvvisoBacheca> entity);
}	