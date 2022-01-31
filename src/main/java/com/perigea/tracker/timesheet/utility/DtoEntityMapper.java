package com.perigea.tracker.timesheet.utility;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.perigea.tracker.timesheet.dto.AnagraficaClienteDto;
import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteInputDto;
import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteResponseDto;
import com.perigea.tracker.timesheet.dto.CommessaDto;
import com.perigea.tracker.timesheet.dto.CommessaFatturabileDto;
import com.perigea.tracker.timesheet.dto.CommessaNonFatturabileDto;
import com.perigea.tracker.timesheet.dto.ContattoDto;
import com.perigea.tracker.timesheet.dto.DipendenteCommessaDto;
import com.perigea.tracker.timesheet.dto.FestivitaDto;
import com.perigea.tracker.timesheet.dto.GruppoContattoDto;
import com.perigea.tracker.timesheet.dto.NotaSpeseDto;
import com.perigea.tracker.timesheet.dto.NotaSpeseInputDto;
import com.perigea.tracker.timesheet.dto.OrdineCommessaDto;
import com.perigea.tracker.timesheet.dto.RuoloDto;
import com.perigea.tracker.timesheet.dto.TimesheetEntryDto;
import com.perigea.tracker.timesheet.dto.TimesheetInputDto;
import com.perigea.tracker.timesheet.dto.TimesheetResponseDto;
import com.perigea.tracker.timesheet.dto.UtentePostDto;
import com.perigea.tracker.timesheet.dto.UtenteViewDto;
import com.perigea.tracker.timesheet.entity.AnagraficaCliente;
import com.perigea.tracker.timesheet.entity.AnagraficaDipendente;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.CommessaFatturabile;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.entity.Contatto;
import com.perigea.tracker.timesheet.entity.DipendenteCommessa;
import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.entity.Gruppo;
import com.perigea.tracker.timesheet.entity.NotaSpese;
import com.perigea.tracker.timesheet.entity.OrdineCommessa;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.TimesheetEntry;
import com.perigea.tracker.timesheet.entity.Utente;


@Mapper
public interface DtoEntityMapper {
	
	DtoEntityMapper INSTANCE = Mappers.getMapper(DtoEntityMapper.class);

	Utente fromDtoToEntityUtente(UtentePostDto dto);

	@Mapping(ignore = true, target = "password")
	UtentePostDto fromEntityToDtoUtente(Utente entity);
	
	UtenteViewDto fromEntityToUtenteViewDto(Utente entity);
	
	List<RuoloDto> fromEntityToDto(List<Ruolo> list);
	
	List<Ruolo> fromDtoToEntity(List<RuoloDto> list);
	
	AnagraficaCliente fromDtoToEntityAnagraficaCliente(AnagraficaClienteDto dto);

	AnagraficaClienteDto fromEntityToDtoAnagraficaCliente(AnagraficaCliente entity);
	
	AnagraficaDipendente fromDtoToEntityAnagraficaDipendente(AnagraficaDipendenteInputDto dto);
	
	AnagraficaDipendenteInputDto fromEntityToDtoAnagraficaDipendente(AnagraficaDipendente entity);
	
	AnagraficaDipendenteResponseDto fromEntityToDtoAnagraficaDipendenteView(AnagraficaDipendente entity);
	
	Ruolo fromDtoToEntityRuoli(RuoloDto dto);
	
	RuoloDto fromEntityToDtoRuoli(Ruolo entity);
	
	Commessa fromDtoToEntityCommessa(CommessaDto dto);
	
	CommessaDto fromEntityToDtoCommessa(Commessa entity);
	
	@Mapping(target= "codiceCommessa", source= "commessa.codiceCommessa")
	@Mapping(target= "tipoCommessa", source= "commessa.tipoCommessa")
	@Mapping(target= "descrizioneCommessa", source= "commessa.descrizioneCommessa")
	CommessaFatturabile fromDtoToEntityCommessaFatturabile(CommessaFatturabileDto dto);
	
	@Mapping(target= "commessa.codiceCommessa", source="codiceCommessa")
	@Mapping(target= "commessa.tipoCommessa", source="tipoCommessa")
	@Mapping(target= "commessa.descrizioneCommessa", source= "descrizioneCommessa")
	CommessaFatturabileDto fromEntityToDtoCommessaFatturabile(CommessaFatturabile entity);
	
	CommessaNonFatturabile fromDtoToEntityCommessaNonFatturabile(CommessaNonFatturabileDto dto);
	
	CommessaNonFatturabileDto fromEntityToDtoCommessaNonFatturabile(CommessaNonFatturabile entity);
	
	@Mapping(target= "id.codiceCommessa", source="codiceCommessa")
	@Mapping(target= "id.numeroOrdineCliente", source="numeroOrdineCliente")
	@Mapping(target= "id.partitaIva", source="partitaIva")
	OrdineCommessa fromDtoToEntityOrdineCommessa(OrdineCommessaDto dto);
	
	@Mapping(target= ".", source="id")
	OrdineCommessaDto fromEntityToDtoOrdineCommessa(OrdineCommessa entity);
	
	List<TimesheetEntry> fromEntityToDtoTimesheetData(List<TimesheetEntryDto> dtos);
	
	List<TimesheetEntryDto> fromDtoToEntityTimesheetData(List<TimesheetEntry> entities);

	@Mapping(target= "id.anno", source="anno")
	@Mapping(target= "id.mese", source="mese")
	@Mapping(target= "id.codicePersona", source="codicePersona")
	Timesheet fromDtoToEntityMensile(TimesheetInputDto dto);
	
	Festivita FromDtoToEntityFestivita(FestivitaDto dto);
	
	FestivitaDto FromEntityToDtoFestivita(Festivita entity);
	
	@Mapping(target= "id.costoNotaSpese", source="costoNotaSpese")
	NotaSpese fromDtoToEntityNotaSpese (NotaSpeseDto dto);
	
	@Mapping(target= "id.anno", source="anno")
	@Mapping(target= "id.mese", source="mese")
	@Mapping(target= "id.costoNotaSpese", source="costoNotaSpese")
	NotaSpese fromDtoToEntityNotaSpeseInput (NotaSpeseInputDto dto);
	
	@Mapping(target= ".", source="id")
	NotaSpeseDto fromEntityToDtoNotaSpese (NotaSpese entity);
	
	@Mapping(target= "id.giorno", source="giorno")
	@Mapping(target= "id.codicePersona", source="codicePersona")
	@Mapping(target= "id.codiceCommessa", source="codiceCommessa")
	List<NotaSpese> fromDtoToEntityNotaSpese (List<NotaSpeseDto> dto);
	
	List<NotaSpeseDto> fromEntityToDtoNotaSpese (List<NotaSpese> entity);

	@Mapping(target= "id.anno", source="anno")
	@Mapping(target= "id.mese", source="mese")
	@Mapping(target= "id.costoNotaSpese", source="costoNotaSpese")
	NotaSpese fromDtoToEntityNotaSpese(NotaSpeseInputDto notaSpeseDto);
	
	@Mapping(target= "id.codicePersona", source="codicePersona")
	@Mapping(target= "id.codiceCommessa", source="codiceCommessa")
	DipendenteCommessa fromDtoToEntityRelazioneDipendenteCommessa(DipendenteCommessaDto dto);
	
	@Mapping(target= ".", source="id")
	DipendenteCommessaDto fromEntityToDtoDipendenteCommessa(DipendenteCommessa entity);
	
	Contatto fromDtoToEntityContatto(ContattoDto contatto);
	List<Contatto> fromDtoToEntityContatto(List<ContattoDto> contatto);
	ContattoDto fromEntityToDtoContatto(Contatto contatto);
	List<ContattoDto> fromEntityToDtoContatto(List<Contatto> contatto);
	Gruppo fromDtoToEntityGruppo(GruppoContattoDto gruppo);
	GruppoContattoDto fromEntityToDtoGruppo(Gruppo gruppo);
	
	@Mapping(target= ".", source="id")
	@Mapping(target= "nome", source="utente.nome")
	@Mapping(target= "cognome", source="utente.cognome")
	@Mapping (target = "mailAziendale", source = "utente.dipendente.mailAziendale")
	TimesheetResponseDto fromEntityToDtoMensile(Timesheet entity);
	
	@Mapping(target = "id.giorno", source = "giorno")
	@Mapping(target = "id.codiceCommessa", source = "codiceCommessa")
	@Mapping(target = "commessa.descrizioneCommessa", source = "descrizioneCommessa")
	@Mapping(target = "commessa.cliente.ragioneSociale", source = "ragioneSociale")
	TimesheetEntry fromDtoToEntityTimeSheet(TimesheetEntryDto dto);
	
	@Mapping(target= "giorno", source="id.giorno")
	@Mapping(target= "codiceCommessa", source="id.codiceCommessa")
	@Mapping (target = "descrizioneCommessa", source = "commessa.descrizioneCommessa")
	@Mapping (target = "ragioneSociale", source = "commessa.cliente.ragioneSociale")
	TimesheetEntryDto fromEntityToDtoTimeSheet(TimesheetEntry entity);
	
}