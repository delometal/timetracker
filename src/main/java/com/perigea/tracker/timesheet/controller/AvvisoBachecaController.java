package com.perigea.tracker.timesheet.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.commons.dto.AvvisoBachecaDto;
import com.perigea.tracker.commons.dto.NonPersistedEventDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.configuration.ApplicationProperties;
import com.perigea.tracker.timesheet.entity.AvvisoBacheca;
import com.perigea.tracker.timesheet.entity.Dipendente;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.rest.NotificationRestClient;
import com.perigea.tracker.timesheet.service.AvvisoBachecaService;
import com.perigea.tracker.timesheet.service.DipendenteService;

@RestController
@RequestMapping("/bacheca")
public class AvvisoBachecaController {

    @Autowired
    private AvvisoBachecaService avvisoBachecaService;
    
    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private DtoEntityMapper dtoEntityMapper;
    
	@Autowired
	private NotificationRestClient notificationRestClient;
	
	@Autowired
	private ApplicationProperties properties;

    @PostMapping(value = "/create-avviso")
    public ResponseEntity<ResponseDto<AvvisoBachecaDto>> saveAvvisoBacheca(@RequestBody AvvisoBachecaDto avvisoDto){
        AvvisoBacheca avviso = dtoEntityMapper.dtoToEntity(avvisoDto);
        avviso = avvisoBachecaService.createAvvisoBacheca(avviso);
        avvisoDto = dtoEntityMapper.entityToDto(avviso);
        List<Dipendente> all = dipendenteService.getAllActiveDipendenti();
        List<String> allEmail = new ArrayList<>();
        all.forEach(s -> {
        	allEmail.add(s.getUtente().getMailAziendale());
        });
       avvisoDto.setRecipients(allEmail);
       notificationRestClient.sendInstantNotification(new NonPersistedEventDto<AvvisoBachecaDto>(AvvisoBachecaDto.class, Utils.toJson(avvisoDto)), 
        		properties.getAvvisoBachecaEndpoint());
        ResponseDto<AvvisoBachecaDto> genericResponse = ResponseDto.<AvvisoBachecaDto>builder().data(avvisoDto).build();
        return ResponseEntity.ok(genericResponse);
    }

    @GetMapping(value = "/read-all")
    public ResponseEntity<ResponseDto<List<AvvisoBachecaDto>>> readAllAvvisi(){
        List<AvvisoBacheca> avvisi = avvisoBachecaService.readAllAvvisi();
        List<AvvisoBachecaDto> avvisiDto = dtoEntityMapper.avvisoEntityToDtoList(avvisi);
        ResponseDto<List<AvvisoBachecaDto>> genericResponse = ResponseDto.<List<AvvisoBachecaDto>>builder().data(avvisiDto).build();
        return ResponseEntity.ok(genericResponse);
    }

    @GetMapping(value = "/read-avviso/{id}")
    public ResponseEntity<ResponseDto<AvvisoBachecaDto>> readAvviso(@PathVariable Long id){
        AvvisoBacheca avviso = avvisoBachecaService.readAvvisoBacheca(id);
        AvvisoBachecaDto avvisoDto = dtoEntityMapper.entityToDto(avviso);
        ResponseDto<AvvisoBachecaDto> genericResponse = ResponseDto.<AvvisoBachecaDto>builder().data(avvisoDto).build();
        return ResponseEntity.ok(genericResponse);
    }

    @PutMapping(value = "/update-avviso")
    public ResponseEntity<ResponseDto<AvvisoBachecaDto>> updateAvviso(@RequestBody AvvisoBachecaDto avvisoDto){
        AvvisoBacheca avviso = dtoEntityMapper.dtoToEntity(avvisoDto);
        avviso = avvisoBachecaService.updateAvviso(avviso);
        avvisoDto = dtoEntityMapper.entityToDto(avviso);
        ResponseDto<AvvisoBachecaDto> genericResponse = ResponseDto.<AvvisoBachecaDto>builder().data(avvisoDto).build();
        return ResponseEntity.ok(genericResponse);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<ResponseDto<AvvisoBachecaDto>> deleteAvviso(@PathVariable Long id){
        AvvisoBacheca avviso = avvisoBachecaService.readAvvisoBacheca(id);
        AvvisoBachecaDto avvisoDto = dtoEntityMapper.entityToDto(avviso);
        avvisoBachecaService.deleteAvviso(avviso.getId());
        ResponseDto<AvvisoBachecaDto> genericResponse = ResponseDto.<AvvisoBachecaDto>builder().data(avvisoDto).build();
        return ResponseEntity.ok(genericResponse);
    }

}
