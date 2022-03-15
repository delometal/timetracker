package com.perigea.tracker.timesheet.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.dto.CreatedUtenteNotificaDto;
import com.perigea.tracker.commons.dto.NonPersistedEventDto;
import com.perigea.tracker.commons.enums.AnagraficaType;
import com.perigea.tracker.commons.enums.RuoloType;
import com.perigea.tracker.commons.enums.StatoUtenteType;
import com.perigea.tracker.commons.exception.CentroDiCostoException;
import com.perigea.tracker.commons.exception.ConsulenteException;
import com.perigea.tracker.commons.exception.PersistenceException;
import com.perigea.tracker.commons.exception.UtenteException;
import com.perigea.tracker.commons.utils.UsernameComparator;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.PasswordToken;
import com.perigea.tracker.timesheet.entity.Personale;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.repository.ApplicationDao;
import com.perigea.tracker.timesheet.repository.PasswordTokenRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;
import com.perigea.tracker.timesheet.rest.RestClient;
import com.perigea.tracker.timesheet.search.Condition;
import com.perigea.tracker.timesheet.search.FilterFactory;
import com.perigea.tracker.timesheet.search.Operator;

@Service
@Transactional
public class UtenteService {
	
	@Autowired
	private FilterFactory<Utente> filter;

	@Autowired
	private Logger logger;

	@Autowired
	private ApplicationDao applicationDao;

	@Autowired
	protected UtenteRepository utenteRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PasswordTokenRepository passwordTokenRepository;

	@Autowired
	private RestClient restClient;

	public <T extends Personale> Utente createUtente(Utente utente, T personale) {
		try {
			utente.setCodicePersona(null);
			personale.setCodicePersona(null);
			String codicePersona = Utils.uuid();
			utente.setCodicePersona(codicePersona);
			String username = this.username(utente.getNome(), utente.getCognome());
			utente.setUsername(username);
			String randomString = Utils.randomString(10);
			String password = passwordEncoder.encode(randomString);
			utente.setPassword(password);
			String token = Utils.uuid();
			PasswordToken passwordToken = PasswordToken.builder().username(username).token(token)
					.dataScadenza(Utils.shifTimeByHour(new Date(), Utils.CREDENTIAL_EXPIRATION_SHIFT_AMOUNT)).build();
			personale.setUtente(utente);
			Utente user = utenteRepository.save(utente);
			passwordTokenRepository.save(passwordToken);

			CreatedUtenteNotificaDto notifica = CreatedUtenteNotificaDto.builder()
					.token(token)
					.mailAziendale(utente.getMailAziendale())
					.nome(utente.getNome())
					.password(randomString)
					.username(username)
					.dataScadenza(passwordToken.getDataScadenza())
					.build();
//			restClient.sendUserNotification(new NonPersistedEventDto<CreatedUtenteNotificaDto>(CreatedUtenteNotificaDto.class, Utils.toJson(notifica)));				
			return user;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new UtenteException(ex.getMessage());
		}
	}

	/**
	 * creazione utente
	 * 
	 * @param utente
	 * @return
	 */
	public Utente saveUtente(Utente utente) {
		try {
			return utenteRepository.save(utente);
		} catch (Exception ex) {
			throw new UtenteException(ex.getMessage());
		}
	}

	/**
	 * lettura utente
	 * 
	 * @param id
	 * @return
	 */
	public Utente readUtente(String id) {
		try {
			Utente u =  utenteRepository.findByCodicePersona(id).get();
			return u;
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new UtenteException(ex.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	public Utente loadUtente(String id) {
		try {
			return utenteRepository.getOne(id);
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new UtenteException(ex.getMessage());
		}
	}

	/**
	 * cancellazione utente
	 * 
	 * @param partitaIva
	 * @return
	 */
	public void deleteUtente(String id) {
		try {
			Utente utente = utenteRepository.getById(id);
			utente.getRuoli().clear();
			utenteRepository.deleteById(id);
			Optional<PasswordToken> passwordToken = passwordTokenRepository.findByUsername(utente.getUsername());
			if(passwordToken.isPresent()) {
				passwordTokenRepository.delete(passwordToken.get());
			}
			logger.info("Utente cancellato");
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new UtenteException(ex.getMessage());
		}
	}

	/**
	 * delete contatto
	 * 
	 * @param contatto
	 */
	public void deleteUtente(Utente contatto) {
		try {
			utenteRepository.delete(contatto);
		} catch (Exception ex) {
			throw new UtenteException(ex.getMessage());
		}
	}

	public Utente updateUtente(Utente utente) {
		try {
			return utenteRepository.save(utente);
		} catch (Exception ex) {
			throw new ConsulenteException(ex.getMessage());
		}
	}

	// Metodo per aggiornare lo stato (attivo/cessato) di un utente
	public Utente updateUtenteStatus(String codicePersona, StatoUtenteType newStatus) {
		try {
			Integer edits = applicationDao.updateUserStatus(codicePersona, newStatus);
			if (edits != null && edits == 1) {
				return utenteRepository.findByCodicePersona(codicePersona).get();
			} else {
				throw new PersistenceException(String.format(
						"Si è verificato un errore durante l'aggiornamento per l'utente %s con il nuovo stato %s",
						codicePersona, newStatus.name()));
			}
		} catch (Exception ex) {
			throw ex;
		}
	}

	public Utente updateUtentePassword(String codicePersona, String newPassword) {
		try {
			String cryptedPassword = passwordEncoder.encode(newPassword);
			Integer edits = applicationDao.updateUserPassword(codicePersona, cryptedPassword);
			if (edits != null && edits == 1) {
				return utenteRepository.findByCodicePersona(codicePersona).get();
			} else {
				throw new PersistenceException(String.format(
						"Si è verificato un errore durante l'aggiornamento per l'utente %s con la nuova password %s",
						codicePersona, cryptedPassword));
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw ex;
		}
	}

	public boolean checkToken(String token) {
		try {
			PasswordToken passwordToken = passwordTokenRepository.findByToken(token).get();
			return (passwordToken.getDataScadenza().after(new Date()));

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw ex;
		}
	}

	public Utente saveContattoEsterno(Utente utente) {
		try {
			utente.setCodicePersona(Utils.uuid());
			utente.setTipo(AnagraficaType.C);
			utente.setRuoli(Arrays.asList(Ruolo.builder().id(RuoloType.P).build()));
			utente.setUsername(username(utente.getNome(), utente.getCognome()));
			utente.setPassword(passwordEncoder.encode(Utils.randomString(10)));
			utente = utenteRepository.save(utente);
			return utente;
		} catch (Exception ex) {
			throw ex;
		}
	}

	public Utente updateContattoEsterno(Utente utente) {
		try {
			utente = utenteRepository.save(utente);
			return utente;
		} catch (Exception ex) {
			throw ex;
		}
	}

	public String username(String nome, String cognome) {
		String username = nome + "." + cognome;
		List<Utente> userList = utenteRepository.findAllByNomeAndCognome(nome, cognome);
		Integer i = userList.size();
		if (i == 0) {
			return username;
		} else {
			List<String> usernames = userList.stream().map(u -> u.getUsername()).collect(Collectors.toList());
			Collections.sort(usernames, new UsernameComparator());
			String lastUsername = usernames.get(usernames.size() - 1);
			String refNum = lastUsername.substring(username.length(), lastUsername.length());
			Integer suffix = !Utils.isEmpty(refNum) ? Integer.parseInt(refNum) + 1 : 1;

			return username + suffix;
		}

	}

	public List<Utente> searchUtenti(String username, AnagraficaType tipoAnagrafica, final StatoUtenteType statoUtente) {
		try {
			return utenteRepository.findAll(utenteByUsernameAndTypeSearch(username, tipoAnagrafica, statoUtente));
		} catch (Exception ex) {
			throw new CentroDiCostoException(ex.getMessage());
		}
	}
	
	private Specification<Utente> utenteByUsernameAndTypeSearch(
			final String username,
			final AnagraficaType tipoAnagrafica,
			final StatoUtenteType statoUtente) {
		
		List<Condition> conditions = new ArrayList<>();
		conditions.add(Condition.builder().field("username").value(username).valueType(String.class).operator(Operator.like).build());
		conditions.add(Condition.builder().field("stato").value(statoUtente).valueType(StatoUtenteType.class).operator(Operator.eq).build());
		conditions.add(Condition.builder().field("tipo").value(tipoAnagrafica).valueType(AnagraficaType.class).operator(Operator.eq).build());
		return filter.buildSpecification(conditions, false);
	}
	
	public List<Utente> searchUtenti(String name, String luogoDiNascita) {
		try {
			return utenteRepository.findAll(utenteByNameAndLuogoDiNascita(name, luogoDiNascita));
		} catch (Exception ex) {
			throw new CentroDiCostoException(ex.getMessage());
		}
	}
	
	private Specification<Utente> utenteByNameAndLuogoDiNascita(
			final String name,
			final String luogoDiNascita) {
		
		List<Condition> conditions = new ArrayList<>();
		conditions.add(Condition.builder().field("name").value(name).valueType(String.class).operator(Operator.like).build());
		conditions.add(Condition.builder().field("luogoDiNascita").value(luogoDiNascita).valueType(String.class).operator(Operator.eq).build());
		return filter.buildSpecification(conditions, false);
	}
	
	
//	public Personale readAnagraficaPersonale(String codicePersona) {
//		try {
//			return utenteRepository.getById(codicePersona).getPersonale();
//		} catch (Exception ex) {
//			if(ex instanceof NoSuchElementException) {
//				throw new EntityNotFoundException(ex.getMessage());
//			}
//			throw new ConsulenteException(ex.getMessage());
//		}
//	}

}
