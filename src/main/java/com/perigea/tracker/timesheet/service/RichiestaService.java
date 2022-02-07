package com.perigea.tracker.timesheet.service;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.commons.exception.RichiestaException;
import com.perigea.tracker.timesheet.entity.Richiesta;
import com.perigea.tracker.timesheet.entity.RichiestaHistory;
import com.perigea.tracker.timesheet.repository.RichiestaRepository;

@Service
@Transactional
public class RichiestaService {

	@Autowired
	private Logger logger;

	@Autowired
	private RichiestaRepository richiestaRepository;
	
	public Richiesta createRichiesta(Richiesta richiesta) {
		try {
			richiestaRepository.save(richiesta);
			return richiesta;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new RichiestaException(ex.getMessage());
		}
	}

	public Richiesta readRichiesta(Integer codiceRichiesta) {
		try {
			Richiesta richiesta = richiestaRepository.findById(codiceRichiesta).get();
			richiesta.getHistory(); //load history in session
			return richiesta;
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new RichiestaException(ex.getMessage());
		}
	}

	public Richiesta updateRichiesta(Richiesta richiesta) {
		try {
			return richiestaRepository.save(richiesta);
		} catch (Exception ex) {
			throw new RichiestaException(ex.getMessage());
		}
	}

	public void deleteRichiesta(Richiesta richiesta) {
		try {
			richiestaRepository.delete(richiesta);
		} catch (Exception ex) {
			throw new RichiestaException(ex.getMessage());
		}
	}
	
	public void deleteRichiesta(Integer id) {
		try {
			richiestaRepository.deleteById(id);
		} catch (Exception ex) {
			throw new RichiestaException(ex.getMessage());
		}
	}

	public Richiesta updateRichiestaHistory(RichiestaHistory history) {
		try {
			Richiesta richiesta = richiestaRepository.findById(history.getRichiesta().getCodiceRichiesta()).get();
			RichiestaHistory historyOld = null;
			for(RichiestaHistory h : richiesta.getHistory()) {
				if(h.getCodiceRichiestaHistory() == history.getCodiceRichiestaHistory()) {
					historyOld = h;
					break;
				}
			}
			if(historyOld != null) {
				richiesta.getHistory().remove(historyOld);
			}
			richiesta.addRichiestaHistory(history);
			return richiestaRepository.save(richiesta);
		} catch (Exception ex) {
			throw new RichiestaException(ex.getMessage());
		}
	}

	public Richiesta deleteRichiestaHistory(RichiestaHistory history) {
		try {
			Richiesta richiesta = richiestaRepository.findById(history.getRichiesta().getCodiceRichiesta()).get();
			RichiestaHistory historyOld = null;
			for(RichiestaHistory h : richiesta.getHistory()) {
				if(h.getCodiceRichiestaHistory() == history.getCodiceRichiestaHistory()) {
					historyOld = h;
					break;
				}
			}
			if(historyOld != null) {
				richiesta.getHistory().remove(historyOld);
			}
			return richiestaRepository.save(richiesta);
		} catch (Exception ex) {
			throw new RichiestaException(ex.getMessage());
		}
	}

}