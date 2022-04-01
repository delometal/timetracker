package com.perigea.tracker.timesheet.service;

import com.perigea.tracker.commons.exception.AvvisoBachecaException;
import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.entity.AvvisoBacheca;
import com.perigea.tracker.timesheet.repository.AvvisoBachecaRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AvvisoBachecaService {

    @Autowired
    private AvvisoBachecaRepository avvisoBachecaRepository;

    @Autowired
    private Logger logger;

    public AvvisoBacheca createAvvisoBacheca(AvvisoBacheca avviso){
        try {
            return avvisoBachecaRepository.save(avviso);
        } catch (Exception e){
            throw new AvvisoBachecaException("Avviso non salvato!");
        }
    }

    public AvvisoBacheca readAvvisoBacheca(Long id) {
        try {
            return avvisoBachecaRepository.findById(id).orElseThrow();
        } catch (Exception ex) {
            if(ex instanceof NoSuchElementException) {
                throw new EntityNotFoundException(ex.getMessage());
            }
            throw new EntityNotFoundException(String.format("%s not found", id));
        }
    }

    public List<AvvisoBacheca> readAllAvvisi(){
        try {
            return avvisoBachecaRepository.findAll();
        } catch (Exception e) {
            throw new AvvisoBachecaException(e.getMessage());
        }
    }

    public AvvisoBacheca updateAvviso(AvvisoBacheca avviso) {
        try {
            return avvisoBachecaRepository.save(avviso);
        } catch (Exception ex) {
            throw new AvvisoBachecaException(ex.getMessage());
        }
    }

    public void deleteAvviso(long id){
        try {
            avvisoBachecaRepository.deleteById(id);
            logger.info(String.format("Avviso con id %s cancellato", id));
        } catch (Exception ex) {
            if(ex instanceof NoSuchElementException) {
                throw new EntityNotFoundException(ex.getMessage());
            }
            throw new AvvisoBachecaException(ex.getMessage());
        }
    }

}
