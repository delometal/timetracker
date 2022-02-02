package com.perigea.tracker.timesheet.mapper;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.perigea.tracker.commons.dto.AnagraficaDto;
import com.perigea.tracker.commons.dto.ClienteDto;
import com.perigea.tracker.commons.dto.CommessaDto;
import com.perigea.tracker.commons.dto.CommessaFatturabileDto;
import com.perigea.tracker.commons.dto.CommessaNonFatturabileDto;
import com.perigea.tracker.commons.dto.ConsulenteDto;
import com.perigea.tracker.commons.dto.ContattoDto;
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
import com.perigea.tracker.commons.dto.RuoloDto;
import com.perigea.tracker.commons.dto.TimesheetEntryDto;
import com.perigea.tracker.commons.dto.TimesheetRefDto;
import com.perigea.tracker.commons.dto.TimesheetResponseDto;
import com.perigea.tracker.commons.dto.UtenteDto;
import com.perigea.tracker.timesheet.entity.Anagrafica;
import com.perigea.tracker.timesheet.entity.Cliente;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.CommessaFatturabile;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.entity.Consulente;
import com.perigea.tracker.timesheet.entity.DatiEconomiciConsulente;
import com.perigea.tracker.timesheet.entity.DatiEconomiciDipendente;
import com.perigea.tracker.timesheet.entity.Dipendente;
import com.perigea.tracker.timesheet.entity.DipendenteCommessa;
import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.entity.Fornitore;
import com.perigea.tracker.timesheet.entity.Gruppo;
import com.perigea.tracker.timesheet.entity.NotaSpese;
import com.perigea.tracker.timesheet.entity.OrdineCommessa;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.TimesheetEntry;
import com.perigea.tracker.timesheet.entity.Utente;

@Mapper
public interface DtoEntityMapper {
	
	Utente dtoToEntity(UtenteDto dto);

	@Mapping(ignore = true, target = "password")
	UtenteDto entityToDto(Utente entity);
		
	List<RuoloDto> dtoToEntity(List<Ruolo> list);
	List<Ruolo> dtoToEntityRuoloList(List<RuoloDto> list);
	
	Cliente dtoToEntity(ClienteDto dto);
	ClienteDto entityToDto(Cliente entity);
	
	Fornitore dtoToEntity(FornitoreDto dto);
	FornitoreDto entityToDto(Fornitore entity);
	
	Dipendente dtoToEntity(DipendenteDto dto);
	DipendenteDto entityToDto(Dipendente entity);
		
	Consulente dtoToEntity(ConsulenteDto dto);
	ConsulenteDto entityToDto(Consulente entity);
		
	Ruolo dtoToEntity(RuoloDto dto);
	RuoloDto entityToDto(Ruolo entity);
	
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
	
	CommessaNonFatturabile dtoToEntity(CommessaNonFatturabileDto dto);
	CommessaNonFatturabileDto entityToDto(CommessaNonFatturabile entity);
	
	@Mapping(target= "id.codiceCommessa", source="codiceCommessa")
	@Mapping(target= "id.numeroOrdineCliente", source="numeroOrdineCliente")
	@Mapping(target= "id.codiceAzienda", source="codiceAzienda")
	OrdineCommessa dtoToEntity(OrdineCommessaDto dto);
	
	@Mapping(target= ".", source="id")
	OrdineCommessaDto entityToDto(OrdineCommessa entity);

	@Mapping(target= "id.anno", source="anno")
	@Mapping(target= "id.mese", source="mese")
	@Mapping(target= "id.codicePersona", source="codicePersona")
	Timesheet dtoToEntity(TimesheetRefDto dto);
	
	Festivita dtoToEntity(FestivitaDto dto);
	FestivitaDto entityToDto(Festivita entity);
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
	List<NotaSpese> dtoToEntityList(List<NotaSpeseDto> dto);
	List<NotaSpeseDto> entityToDtoNotaSpeseList(List<NotaSpese> entity);

	@Mapping(target= "id.codicePersona", source="codicePersona")
	@Mapping(target= "id.codiceCommessa", source="codiceCommessa")
	DipendenteCommessa dtoToEntity(DipendenteCommessaDto dto);
	
	@Mapping(target= ".", source="id")
	DipendenteCommessaDto entityToDto(DipendenteCommessa entity);
	
	Anagrafica dtoToEntity(ContattoDto contatto);
	Anagrafica dtoToEntity(AnagraficaDto anagrafica);
	List<Anagrafica> dtoToEntityAnagraficaList(List<AnagraficaDto> anagrafica);
	AnagraficaDto entityToDto(Anagrafica anagrafica);
	List<AnagraficaDto> entityToDtoAnagraficaList(List<Anagrafica> anagrafica);
	
	Gruppo dtoToEntity(GruppoContattoDto gruppo);
	GruppoContattoDto entityToDto(Gruppo gruppo);
	
	DatiEconomiciDipendenteDto entityToDto(DatiEconomiciDipendente entity);
	DatiEconomiciDipendente dtoToEntity(DatiEconomiciDipendenteDto dto);
	
	DatiEconomiciConsulenteDto entityToDto(DatiEconomiciConsulente entity);
	DatiEconomiciConsulente dtoToEntity(DatiEconomiciConsulenteDto dto);
	
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
	@Mapping(target= "nome", source="utente.anagrafica.nome")
	@Mapping(target= "cognome", source="utente.anagrafica.cognome")
	@Mapping (target = "mailAziendale", source = "utente.anagrafica.mailAziendale")
	TimesheetResponseDto entityToDto(Timesheet entity);
	
}