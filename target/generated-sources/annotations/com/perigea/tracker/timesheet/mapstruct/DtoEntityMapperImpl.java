package com.perigea.tracker.timesheet.mapstruct;

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
import com.perigea.tracker.timesheet.enumerator.StatoType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-13T12:38:03+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class DtoEntityMapperImpl implements DtoEntityMapper {

    @Override
    public Utente fromDtoToEntityUtente(UtentePostDto dto) {
        if ( dto == null ) {
            return null;
        }

        Utente utente = new Utente();

        utente.setCreateUser( dto.getCreateUser() );
        utente.setLastUpdateUser( dto.getLastUpdateUser() );
        utente.setCodicePersona( dto.getCodicePersona() );
        utente.setNome( dto.getNome() );
        utente.setCognome( dto.getCognome() );
        utente.setPassword( dto.getPassword() );
        utente.setStatoUtenteType( dto.getStatoUtenteType() );

        return utente;
    }

    @Override
    public UtentePostDto fromEntityToDtoUtente(Utente entity) {
        if ( entity == null ) {
            return null;
        }

        UtentePostDto utentePostDto = new UtentePostDto();

        utentePostDto.setCodicePersona( entity.getCodicePersona() );
        utentePostDto.setNome( entity.getNome() );
        utentePostDto.setCognome( entity.getCognome() );
        utentePostDto.setPassword( entity.getPassword() );
        utentePostDto.setStatoUtenteType( entity.getStatoUtenteType() );
        utentePostDto.setCreateUser( entity.getCreateUser() );
        utentePostDto.setLastUpdateUser( entity.getLastUpdateUser() );

        return utentePostDto;
    }

    @Override
    public UtenteViewDto fromEntityToUtenteViewDto(Utente entity) {
        if ( entity == null ) {
            return null;
        }

        UtenteViewDto utenteViewDto = new UtenteViewDto();

        utenteViewDto.setCodicePersona( entity.getCodicePersona() );
        utenteViewDto.setNome( entity.getNome() );
        utenteViewDto.setCognome( entity.getCognome() );
        utenteViewDto.setStatoUtenteType( entity.getStatoUtenteType() );

        return utenteViewDto;
    }

    @Override
    public AnagraficaCliente fromDtoToEntityAnagraficaCliente(AnagraficaClienteDto dto) {
        if ( dto == null ) {
            return null;
        }

        AnagraficaCliente anagraficaCliente = new AnagraficaCliente();

        anagraficaCliente.setCreateUser( dto.getCreateUser() );
        anagraficaCliente.setLastUpdateUser( dto.getLastUpdateUser() );
        anagraficaCliente.setRagioneSocialeCliente( dto.getRagioneSocialeCliente() );
        anagraficaCliente.setPartitaIva( dto.getPartitaIva() );
        anagraficaCliente.setCodiceFiscale( dto.getCodiceFiscale() );
        anagraficaCliente.setCodiceDestinatario( dto.getCodiceDestinatario() );
        anagraficaCliente.setSedeLegaleComune( dto.getSedeLegaleComune() );
        anagraficaCliente.setSedeLegaleCap( dto.getSedeLegaleCap() );
        anagraficaCliente.setSedeLegaleIndirizzo( dto.getSedeLegaleIndirizzo() );
        anagraficaCliente.setSedeOperativaComune( dto.getSedeOperativaComune() );
        anagraficaCliente.setSedeOperativaCap( dto.getSedeOperativaCap() );
        anagraficaCliente.setSedeOperativaIndirizzo( dto.getSedeOperativaIndirizzo() );
        anagraficaCliente.setAcronimoCliente( dto.getAcronimoCliente() );
        anagraficaCliente.setProgressivoPerCommesse( dto.getProgressivoPerCommesse() );
        anagraficaCliente.setTipologiaPagamentoType( dto.getTipologiaPagamentoType() );
        anagraficaCliente.setNotePerLaFatturazione( dto.getNotePerLaFatturazione() );

        return anagraficaCliente;
    }

    @Override
    public AnagraficaClienteDto fromEntityToDtoAnagraficaCliente(AnagraficaCliente entity) {
        if ( entity == null ) {
            return null;
        }

        AnagraficaClienteDto anagraficaClienteDto = new AnagraficaClienteDto();

        anagraficaClienteDto.setRagioneSocialeCliente( entity.getRagioneSocialeCliente() );
        anagraficaClienteDto.setPartitaIva( entity.getPartitaIva() );
        anagraficaClienteDto.setCodiceFiscale( entity.getCodiceFiscale() );
        anagraficaClienteDto.setCodiceDestinatario( entity.getCodiceDestinatario() );
        anagraficaClienteDto.setSedeLegaleComune( entity.getSedeLegaleComune() );
        anagraficaClienteDto.setSedeLegaleCap( entity.getSedeLegaleCap() );
        anagraficaClienteDto.setSedeLegaleIndirizzo( entity.getSedeLegaleIndirizzo() );
        anagraficaClienteDto.setSedeOperativaComune( entity.getSedeOperativaComune() );
        anagraficaClienteDto.setSedeOperativaCap( entity.getSedeOperativaCap() );
        anagraficaClienteDto.setSedeOperativaIndirizzo( entity.getSedeOperativaIndirizzo() );
        anagraficaClienteDto.setAcronimoCliente( entity.getAcronimoCliente() );
        anagraficaClienteDto.setProgressivoPerCommesse( entity.getProgressivoPerCommesse() );
        anagraficaClienteDto.setTipologiaPagamentoType( entity.getTipologiaPagamentoType() );
        anagraficaClienteDto.setNotePerLaFatturazione( entity.getNotePerLaFatturazione() );
        anagraficaClienteDto.setCreateUser( entity.getCreateUser() );
        anagraficaClienteDto.setLastUpdateUser( entity.getLastUpdateUser() );

        return anagraficaClienteDto;
    }

    @Override
    public AnagraficaDipendente fromDtoToEntityAnagraficaDipendente(AnagraficaDipendenteDto dto) {
        if ( dto == null ) {
            return null;
        }

        AnagraficaDipendente anagraficaDipendente = new AnagraficaDipendente();

        anagraficaDipendente.setCreateUser( dto.getCreateUser() );
        anagraficaDipendente.setLastUpdateUser( dto.getLastUpdateUser() );
        anagraficaDipendente.setCodicePersona( dto.getCodicePersona() );
        anagraficaDipendente.setLuogoDiNascita( dto.getLuogoDiNascita() );
        anagraficaDipendente.setDataDiNascita( dto.getDataDiNascita() );
        anagraficaDipendente.setMailAziendale( dto.getMailAziendale() );
        anagraficaDipendente.setMailPrivata( dto.getMailPrivata() );
        anagraficaDipendente.setCellulare( dto.getCellulare() );
        anagraficaDipendente.setProvinciaDiDomicilio( dto.getProvinciaDiDomicilio() );
        anagraficaDipendente.setComuneDiDomicilio( dto.getComuneDiDomicilio() );
        anagraficaDipendente.setIndirizzoDiDomicilio( dto.getIndirizzoDiDomicilio() );
        anagraficaDipendente.setProvinciaDiResidenza( dto.getProvinciaDiResidenza() );
        anagraficaDipendente.setComuneDiResidenza( dto.getComuneDiResidenza() );
        anagraficaDipendente.setIndirizzoDiResidenza( dto.getIndirizzoDiResidenza() );
        anagraficaDipendente.setNomeContattoEmergenza( dto.getNomeContattoEmergenza() );
        anagraficaDipendente.setCellulareContattoEmergenza( dto.getCellulareContattoEmergenza() );
        anagraficaDipendente.setDataAssunzione( dto.getDataAssunzione() );
        anagraficaDipendente.setIban( dto.getIban() );
        anagraficaDipendente.setDataCessazione( dto.getDataCessazione() );
        anagraficaDipendente.setCodiceFiscale( dto.getCodiceFiscale() );

        return anagraficaDipendente;
    }

    @Override
    public AnagraficaDipendenteDto fromEntityToDtoAnagraficaDipendente(AnagraficaDipendente entity) {
        if ( entity == null ) {
            return null;
        }

        AnagraficaDipendenteDto anagraficaDipendenteDto = new AnagraficaDipendenteDto();

        anagraficaDipendenteDto.setCodicePersona( entity.getCodicePersona() );
        anagraficaDipendenteDto.setLuogoDiNascita( entity.getLuogoDiNascita() );
        anagraficaDipendenteDto.setDataDiNascita( entity.getDataDiNascita() );
        anagraficaDipendenteDto.setMailAziendale( entity.getMailAziendale() );
        anagraficaDipendenteDto.setMailPrivata( entity.getMailPrivata() );
        anagraficaDipendenteDto.setCellulare( entity.getCellulare() );
        anagraficaDipendenteDto.setProvinciaDiDomicilio( entity.getProvinciaDiDomicilio() );
        anagraficaDipendenteDto.setComuneDiDomicilio( entity.getComuneDiDomicilio() );
        anagraficaDipendenteDto.setIndirizzoDiDomicilio( entity.getIndirizzoDiDomicilio() );
        anagraficaDipendenteDto.setProvinciaDiResidenza( entity.getProvinciaDiResidenza() );
        anagraficaDipendenteDto.setComuneDiResidenza( entity.getComuneDiResidenza() );
        anagraficaDipendenteDto.setIndirizzoDiResidenza( entity.getIndirizzoDiResidenza() );
        anagraficaDipendenteDto.setNomeContattoEmergenza( entity.getNomeContattoEmergenza() );
        anagraficaDipendenteDto.setCellulareContattoEmergenza( entity.getCellulareContattoEmergenza() );
        anagraficaDipendenteDto.setDataAssunzione( entity.getDataAssunzione() );
        anagraficaDipendenteDto.setIban( entity.getIban() );
        anagraficaDipendenteDto.setDataCessazione( entity.getDataCessazione() );
        anagraficaDipendenteDto.setCodiceFiscale( entity.getCodiceFiscale() );
        anagraficaDipendenteDto.setCreateUser( entity.getCreateUser() );
        anagraficaDipendenteDto.setLastUpdateUser( entity.getLastUpdateUser() );

        return anagraficaDipendenteDto;
    }

    @Override
    public Ruoli fromDtoToEntityRuoli(RuoliDto dto) {
        if ( dto == null ) {
            return null;
        }

        Ruoli ruoli = new Ruoli();

        ruoli.setRuoloType( dto.getRuoloType() );
        ruoli.setDescrizioneRuolo( dto.getDescrizioneRuolo() );

        return ruoli;
    }

    @Override
    public RuoliDto fromEntityToDtoRuoli(Ruoli entity) {
        if ( entity == null ) {
            return null;
        }

        RuoliDto ruoliDto = new RuoliDto();

        ruoliDto.setRuoloType( entity.getRuoloType() );
        ruoliDto.setDescrizioneRuolo( entity.getDescrizioneRuolo() );

        return ruoliDto;
    }

    @Override
    public Commessa fromDtoToEntityCommessa(CommessaDto dto) {
        if ( dto == null ) {
            return null;
        }

        Commessa commessa = new Commessa();

        commessa.setCreateUser( dto.getCreateUser() );
        commessa.setLastUpdateUser( dto.getLastUpdateUser() );
        commessa.setCodiceCommessa( dto.getCodiceCommessa() );

        return commessa;
    }

    @Override
    public CommessaDto fromEntityToDtoCommessa(Commessa entity) {
        if ( entity == null ) {
            return null;
        }

        CommessaDto commessaDto = new CommessaDto();

        commessaDto.setCodiceCommessa( entity.getCodiceCommessa() );
        commessaDto.setCreateUser( entity.getCreateUser() );
        commessaDto.setLastUpdateUser( entity.getLastUpdateUser() );

        return commessaDto;
    }

    @Override
    public CommessaFatturabile fromDtoToEntityCommessaFatturabile(CommessaFatturabileDto dto) {
        if ( dto == null ) {
            return null;
        }

        CommessaFatturabile commessaFatturabile = new CommessaFatturabile();

        commessaFatturabile.setCreateUser( dto.getCreateUser() );
        commessaFatturabile.setLastUpdateUser( dto.getLastUpdateUser() );
        commessaFatturabile.setCodiceCommessa( dto.getCodiceCommessa() );
        commessaFatturabile.setDescrizioneCommessaPerigea( dto.getDescrizioneCommessaPerigea() );
        commessaFatturabile.setDescrizioneCommessaCliente( dto.getDescrizioneCommessaCliente() );
        commessaFatturabile.setDataInizioCommessa( dto.getDataInizioCommessa() );
        commessaFatturabile.setDataFineCommessa( dto.getDataFineCommessa() );
        commessaFatturabile.setImportoCommessaInizialePresunto( dto.getImportoCommessaInizialePresunto() );
        commessaFatturabile.setTotaleEstensioni( dto.getTotaleEstensioni() );
        commessaFatturabile.setOrdineInternoCorrente( dto.getOrdineInternoCorrente() );
        commessaFatturabile.setTotaleOrdineClienteFormale( dto.getTotaleOrdineClienteFormale() );
        commessaFatturabile.setTotaleOrdine( dto.getTotaleOrdine() );
        commessaFatturabile.setTotaleRicaviDaInizioCommessa( dto.getTotaleRicaviDaInizioCommessa() );
        commessaFatturabile.setTotaleRicaviDaInizioAnno( dto.getTotaleRicaviDaInizioAnno() );
        commessaFatturabile.setTotaleCostiDaInizioCommessa( dto.getTotaleCostiDaInizioCommessa() );
        commessaFatturabile.setTotaleCostiDaInizioAnno( dto.getTotaleCostiDaInizioAnno() );
        commessaFatturabile.setTotaleFatturatoreDaInizioCommessa( dto.getTotaleFatturatoreDaInizioCommessa() );
        commessaFatturabile.setTotaleFatturatoDaInizioAnno( dto.getTotaleFatturatoDaInizioAnno() );
        commessaFatturabile.setMargineIniziale( dto.getMargineIniziale() );
        commessaFatturabile.setMargineDaInizioCommessa( dto.getMargineDaInizioCommessa() );
        commessaFatturabile.setMargineDaInizioAnno( dto.getMargineDaInizioAnno() );
        commessaFatturabile.setPercentualeAvanzamentoCosti( dto.getPercentualeAvanzamentoCosti() );
        commessaFatturabile.setPercentualeAvanzamentoFatturazione( dto.getPercentualeAvanzamentoFatturazione() );
        commessaFatturabile.setPercentualeSconto( dto.getPercentualeSconto() );
        commessaFatturabile.setResponsabileCommerciale( dto.getResponsabileCommerciale() );

        return commessaFatturabile;
    }

    @Override
    public CommessaFatturabileDto fromEntityToDtoCommessaFatturabile(CommessaFatturabile entity) {
        if ( entity == null ) {
            return null;
        }

        CommessaFatturabileDto commessaFatturabileDto = new CommessaFatturabileDto();

        commessaFatturabileDto.setCodiceCommessa( entity.getCodiceCommessa() );
        commessaFatturabileDto.setCreateUser( entity.getCreateUser() );
        commessaFatturabileDto.setLastUpdateUser( entity.getLastUpdateUser() );
        commessaFatturabileDto.setDescrizioneCommessaPerigea( entity.getDescrizioneCommessaPerigea() );
        commessaFatturabileDto.setDescrizioneCommessaCliente( entity.getDescrizioneCommessaCliente() );
        commessaFatturabileDto.setDataInizioCommessa( entity.getDataInizioCommessa() );
        commessaFatturabileDto.setDataFineCommessa( entity.getDataFineCommessa() );
        commessaFatturabileDto.setImportoCommessaInizialePresunto( entity.getImportoCommessaInizialePresunto() );
        commessaFatturabileDto.setTotaleEstensioni( entity.getTotaleEstensioni() );
        commessaFatturabileDto.setOrdineInternoCorrente( entity.getOrdineInternoCorrente() );
        commessaFatturabileDto.setTotaleOrdineClienteFormale( entity.getTotaleOrdineClienteFormale() );
        commessaFatturabileDto.setTotaleOrdine( entity.getTotaleOrdine() );
        commessaFatturabileDto.setTotaleRicaviDaInizioCommessa( entity.getTotaleRicaviDaInizioCommessa() );
        commessaFatturabileDto.setTotaleRicaviDaInizioAnno( entity.getTotaleRicaviDaInizioAnno() );
        commessaFatturabileDto.setTotaleCostiDaInizioCommessa( entity.getTotaleCostiDaInizioCommessa() );
        commessaFatturabileDto.setTotaleCostiDaInizioAnno( entity.getTotaleCostiDaInizioAnno() );
        commessaFatturabileDto.setTotaleFatturatoreDaInizioCommessa( entity.getTotaleFatturatoreDaInizioCommessa() );
        commessaFatturabileDto.setTotaleFatturatoDaInizioAnno( entity.getTotaleFatturatoDaInizioAnno() );
        commessaFatturabileDto.setMargineIniziale( entity.getMargineIniziale() );
        commessaFatturabileDto.setMargineDaInizioCommessa( entity.getMargineDaInizioCommessa() );
        commessaFatturabileDto.setMargineDaInizioAnno( entity.getMargineDaInizioAnno() );
        commessaFatturabileDto.setPercentualeAvanzamentoCosti( entity.getPercentualeAvanzamentoCosti() );
        commessaFatturabileDto.setPercentualeAvanzamentoFatturazione( entity.getPercentualeAvanzamentoFatturazione() );
        commessaFatturabileDto.setPercentualeSconto( entity.getPercentualeSconto() );
        commessaFatturabileDto.setResponsabileCommerciale( entity.getResponsabileCommerciale() );

        return commessaFatturabileDto;
    }

    @Override
    public CommessaNonFatturabile fromDtoToEntityCommessaNonFatturabile(CommessaNonFatturabileDto dto) {
        if ( dto == null ) {
            return null;
        }

        CommessaNonFatturabile commessaNonFatturabile = new CommessaNonFatturabile();

        commessaNonFatturabile.setCreateUser( dto.getCreateUser() );
        commessaNonFatturabile.setLastUpdateUser( dto.getLastUpdateUser() );
        commessaNonFatturabile.setDescrizione( dto.getDescrizione() );
        commessaNonFatturabile.setCodiceCommessa( dto.getCodiceCommessa() );

        return commessaNonFatturabile;
    }

    @Override
    public CommessaNonFatturabileDto fromEntityToDtoCommessaNonFatturabile(CommessaNonFatturabile entity) {
        if ( entity == null ) {
            return null;
        }

        CommessaNonFatturabileDto commessaNonFatturabileDto = new CommessaNonFatturabileDto();

        commessaNonFatturabileDto.setCodiceCommessa( entity.getCodiceCommessa() );
        commessaNonFatturabileDto.setCreateUser( entity.getCreateUser() );
        commessaNonFatturabileDto.setLastUpdateUser( entity.getLastUpdateUser() );
        commessaNonFatturabileDto.setDescrizione( entity.getDescrizione() );

        return commessaNonFatturabileDto;
    }

    @Override
    public TimeSheet fromDtoToEntityTimeSheet(TimeSheetDto dto) {
        if ( dto == null ) {
            return null;
        }

        TimeSheet timeSheet = new TimeSheet();

        timeSheet.setCreateUser( dto.getCreateUser() );
        timeSheet.setLastUpdateUser( dto.getLastUpdateUser() );
        timeSheet.setOre( dto.getOre() );
        timeSheet.setTrasferta( dto.getTrasferta() );
        if ( dto.getStatoType() != null ) {
            timeSheet.setStatoType( dto.getStatoType().name() );
        }

        return timeSheet;
    }

    @Override
    public TimeSheetDto fromEntityToDtoTimeSheet(TimeSheet entity) {
        if ( entity == null ) {
            return null;
        }

        TimeSheetDto timeSheetDto = new TimeSheetDto();

        timeSheetDto.setOre( entity.getOre() );
        timeSheetDto.setTrasferta( entity.getTrasferta() );
        if ( entity.getStatoType() != null ) {
            timeSheetDto.setStatoType( Enum.valueOf( StatoType.class, entity.getStatoType() ) );
        }
        timeSheetDto.setCreateUser( entity.getCreateUser() );
        timeSheetDto.setLastUpdateUser( entity.getLastUpdateUser() );

        return timeSheetDto;
    }

    @Override
    public RelazioneDipendenteCommessa fromDtoToEntityRelazioneDipendenteCommessa(RelazioneDipendenteCommessaDto dto) {
        if ( dto == null ) {
            return null;
        }

        RelazioneDipendenteCommessa relazioneDipendenteCommessa = new RelazioneDipendenteCommessa();

        relazioneDipendenteCommessa.setCreateUser( dto.getCreateUser() );
        relazioneDipendenteCommessa.setLastUpdateUser( dto.getLastUpdateUser() );
        relazioneDipendenteCommessa.setDataInizioAllocazione( dto.getDataInizioAllocazione() );
        relazioneDipendenteCommessa.setDataFineAllocazione( dto.getDataFineAllocazione() );
        relazioneDipendenteCommessa.setTariffa( dto.getTariffa() );
        relazioneDipendenteCommessa.setGiorniPrevisti( dto.getGiorniPrevisti() );
        relazioneDipendenteCommessa.setGiorniErogati( dto.getGiorniErogati() );
        relazioneDipendenteCommessa.setGiorniResidui( dto.getGiorniResidui() );
        relazioneDipendenteCommessa.setImportoPrevisto( dto.getImportoPrevisto() );
        relazioneDipendenteCommessa.setImportoErogato( dto.getImportoErogato() );
        relazioneDipendenteCommessa.setImportoResiduo( dto.getImportoResiduo() );

        return relazioneDipendenteCommessa;
    }

    @Override
    public Festivita FromDtoToEntityFestivita(FestivitaDto dto) {
        if ( dto == null ) {
            return null;
        }

        Festivita festivita = new Festivita();

        festivita.setData( dto.getData() );
        festivita.setNomeFestivo( dto.getNomeFestivo() );

        return festivita;
    }

    @Override
    public FestivitaDto FromEntityToDtoFestivita(Festivita entity) {
        if ( entity == null ) {
            return null;
        }

        FestivitaDto festivitaDto = new FestivitaDto();

        festivitaDto.setData( entity.getData() );
        festivitaDto.setNomeFestivo( entity.getNomeFestivo() );

        return festivitaDto;
    }
}
