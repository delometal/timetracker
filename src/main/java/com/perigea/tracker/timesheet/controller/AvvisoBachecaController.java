package com.perigea.tracker.timesheet.controller;


import com.perigea.tracker.commons.dto.AvvisoBachecaDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.timesheet.entity.AvvisoBacheca;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.AvvisoBachecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bacheca")
public class AvvisoBachecaController {

    @Autowired
    private AvvisoBachecaService avvisoBachecaService;

    @Autowired
    private DtoEntityMapper dtoEntityMapper;

    @PostMapping(value = "/create-avviso")
    public ResponseEntity<ResponseDto<AvvisoBachecaDto>> saveAvvisoBacheca(@RequestBody AvvisoBachecaDto avvisoDto){
        AvvisoBacheca avviso = dtoEntityMapper.dtoToEntity(avvisoDto);
        avviso = avvisoBachecaService.createAvvisoBacheca(avviso);
        avvisoDto = dtoEntityMapper.entityToDto(avviso);
        // TODO aggiungere notifica verso calendar
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
