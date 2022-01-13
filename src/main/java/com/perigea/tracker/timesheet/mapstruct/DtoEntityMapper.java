package com.perigea.tracker.timesheet.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.perigea.tracker.timesheet.dto.AnagraficaClienteDto;
import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteDto;
import com.perigea.tracker.timesheet.dto.CommessaDto;
import com.perigea.tracker.timesheet.dto.CommessaFatturabileDto;
import com.perigea.tracker.timesheet.dto.CommessaNonFatturabileDto;
import com.perigea.tracker.timesheet.dto.FestivitaDto;
import com.perigea.tracker.timesheet.dto.RelazioneDipendenteCommessaDto;
import com.perigea.tracker.timesheet.dto.RuoliDto;
import com.perigea.tracker.timesheet.dto.TimeSheetDto;
import com.perigea.tracker.timesheet.dto.UtentePostDto;
import com.perigea.tracker.timesheet.dto.UtenteViewDto;
import com.perigea.tracker.timesheet.entity.AnagraficaCliente;
import com.perigea.tracker.timesheet.entity.AnagraficaDipendente;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.CommessaFatturabile;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.entity.RelazioneDipendenteCommessa;
import com.perigea.tracker.timesheet.entity.Ruoli;
import com.perigea.tracker.timesheet.entity.TimeSheet;
import com.perigea.tracker.timesheet.entity.Utente;



@Mapper
public interface DtoEntityMapper {
	
	DtoEntityMapper INSTANCE = Mappers.getMapper(DtoEntityMapper.class);

	Utente fromDtoToEntityUtente(UtentePostDto dto);

	UtentePostDto fromEntityToDtoUtente(Utente entity);
	
	UtenteViewDto fromEntityToUtenteViewDto(Utente entity);
	
	AnagraficaCliente fromDtoToEntityAnagraficaCliente(AnagraficaClienteDto dto);

	AnagraficaClienteDto fromEntityToDtoAnagraficaCliente(AnagraficaCliente entity);
	
	AnagraficaDipendente fromDtoToEntityAnagraficaDipendente(AnagraficaDipendenteDto dto);
	
	AnagraficaDipendenteDto fromEntityToDtoAnagraficaDipendente(AnagraficaDipendente entity);
	
	Ruoli fromDtoToEntityRuoli(RuoliDto dto);
	
	RuoliDto fromEntityToDtoRuoli(Ruoli entity);
	
	Commessa fromDtoToEntityCommessa(CommessaDto dto);
	
	CommessaDto fromEntityToDtoCommessa(Commessa entity);
	
	CommessaFatturabile fromDtoToEntityCommessaFatturabile(CommessaFatturabileDto dto);
	
	CommessaFatturabileDto fromEntityToDtoCommessaFatturabile(CommessaFatturabile entity);
	
	CommessaNonFatturabile fromDtoToEntityCommessaNonFatturabile(CommessaNonFatturabileDto dto);
	
	CommessaNonFatturabileDto fromEntityToDtoCommessaNonFatturabile(CommessaNonFatturabile entity);
	
//	OrdineCommessa fromDtoToEntityOrdineCommessa(OrdineCommessaDto dto);
	
	TimeSheet fromDtoToEntityTimeSheet(TimeSheetDto dto);
	
	TimeSheetDto fromEntityToDtoTimeSheet(TimeSheet entity);
	
	RelazioneDipendenteCommessa fromDtoToEntityRelazioneDipendenteCommessa(RelazioneDipendenteCommessaDto dto);
	
	Festivita FromDtoToEntityFestivita(FestivitaDto dto);
	
	FestivitaDto FromEntityToDtoFestivita(Festivita entity);
}
