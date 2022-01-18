package com.perigea.tracker.timesheet.mapstruct;

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
import com.perigea.tracker.timesheet.dto.DipendenteCommessaDto;
import com.perigea.tracker.timesheet.dto.FestivitaDto;
import com.perigea.tracker.timesheet.dto.OrdineCommessaDto;
import com.perigea.tracker.timesheet.dto.RuoloDto;
import com.perigea.tracker.timesheet.dto.TimesheetDataDto;
import com.perigea.tracker.timesheet.dto.TimesheetInputDto;
import com.perigea.tracker.timesheet.dto.TimesheetResponseDto;
import com.perigea.tracker.timesheet.dto.UtentePostDto;
import com.perigea.tracker.timesheet.dto.UtenteViewDto;
import com.perigea.tracker.timesheet.entity.AnagraficaCliente;
import com.perigea.tracker.timesheet.entity.AnagraficaDipendente;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.CommessaFatturabile;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.entity.DipendenteCommessa;
import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.entity.OrdineCommessa;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.TimesheetData;
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
	
	@Mapping (target= "codiceCommessa", source= "commessa.codiceCommessa")
	@Mapping (target= "commessaType", source= "commessa.commessaType")
	@Mapping (target= "descrizioneCommessa", source= "commessa.descrizioneCommessa")
	CommessaFatturabile fromDtoToEntityCommessaFatturabile(CommessaFatturabileDto dto);
	
	@Mapping (target= "commessa.codiceCommessa", source="codiceCommessa")
	@Mapping (target= "commessa.commessaType", source="commessaType")
	@Mapping (target= "commessa.descrizioneCommessa", source= "descrizioneCommessa")
	CommessaFatturabileDto fromEntityToDtoCommessaFatturabile(CommessaFatturabile entity);
	
	CommessaNonFatturabile fromDtoToEntityCommessaNonFatturabile(CommessaNonFatturabileDto dto);
	
	CommessaNonFatturabileDto fromEntityToDtoCommessaNonFatturabile(CommessaNonFatturabile entity);
	
	OrdineCommessa fromDtoToEntityOrdineCommessa(OrdineCommessaDto dto);
	
	@Mapping (target= ".", source="id")
	OrdineCommessaDto fromEntityToDtoOrdineCommessa(OrdineCommessa entity);
	
	TimesheetData fromDtoToEntityTimeSheet(TimesheetDataDto dto);
	
	TimesheetDataDto fromEntityToDtoTimeSheet(TimesheetData entity);
	
	Timesheet fromDtoToEntityMensile(TimesheetInputDto dto);
	
	@Mapping (target= ".", source="id")
	TimesheetResponseDto fromEntityToDtoMensile(Timesheet entity);
	
	DipendenteCommessa fromDtoToEntityRelazioneDipendenteCommessa(DipendenteCommessaDto dto);
	
	Festivita FromDtoToEntityFestivita(FestivitaDto dto);
	
	FestivitaDto FromEntityToDtoFestivita(Festivita entity);
	
//	UtenteRuoli FromDtoToEntityUtenteRuoli(RuoloUtenteDto dto);
//	
//	RuoloUtenteDto FromEntityToDtoUtenteRuoli(UtenteRuoli entity);
}
