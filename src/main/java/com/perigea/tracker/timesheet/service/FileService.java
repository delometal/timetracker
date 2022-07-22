package com.perigea.tracker.timesheet.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.perigea.tracker.commons.exception.FileDownloadException;
import com.perigea.tracker.commons.exception.FileUploadException;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.configuration.ApplicationProperties;
import com.perigea.tracker.timesheet.entity.Azienda;
import com.perigea.tracker.timesheet.entity.Cliente;
import com.perigea.tracker.timesheet.entity.CurriculumVitae;
import com.perigea.tracker.timesheet.entity.Fornitore;
import com.perigea.tracker.timesheet.entity.LogoAzienda;
import com.perigea.tracker.timesheet.entity.ProfileImage;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.repository.ClienteRepository;
import com.perigea.tracker.timesheet.repository.CurriculumVitaeRepository;
import com.perigea.tracker.timesheet.repository.FornitoreRepository;
import com.perigea.tracker.timesheet.repository.ProfileImageRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;

@Service
@Transactional
public class FileService {

	@Autowired
	private Logger logger;
	
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private FornitoreRepository fornitoreRepository;
		
	@Autowired
	private CurriculumVitaeRepository curriculumVitaeRepository;
	
	@Autowired 
	private ProfileImageRepository profileImageRepository;
	
	@PostConstruct
	public void init() {
		try {
			Path uploadRoot = Paths.get(applicationProperties.getUploadDirectory());
			Path archivio = Paths.get(applicationProperties.getCurriculumDiskDir());
			
			if(!Files.exists(uploadRoot, LinkOption.values())) {
				Files.createDirectories(uploadRoot);
				logger.info(String.format("Upload root folder created in: %s ", uploadRoot.toAbsolutePath()));
			}
			if(!Files.exists(archivio, LinkOption.values())) {
				Files.createDirectories(archivio);
				logger.info(String.format("Archivio folder created in: %s ", archivio.toAbsolutePath()));
			}
		} catch (IOException e) {
			throw new FileUploadException("Could not initialize folder for upload!");
		}
	}
	
	/**
	 * upload di un curriculum vitae
	 * @param codicePersona
	 * @param file
	 */
	public void uploadCurriculum(String codicePersona, MultipartFile file) {
		try {
			Utente utente = utenteRepository.findById(codicePersona).orElseThrow();
			String filepath = extractFilename(codicePersona, utente);
			
			if(applicationProperties.isCurriculumDiskPersistence()) {
				Path archivio = loadArchivioFolder();
				Path childPath = archivio.resolve(filepath);
				
				if(!Files.exists(childPath, LinkOption.values())) {
					Files.createDirectory(childPath);
				}
				if(Files.exists(childPath.resolve(file.getOriginalFilename()), LinkOption.values())) {
					Files.delete(childPath.resolve(file.getOriginalFilename()));
				}
				Files.copy(file.getInputStream(), childPath.resolve(file.getOriginalFilename()));
			}
			
			byte[] fileData = IOUtils.toByteArray(file.getInputStream());
//			Optional<CurriculumVitae> cvOpt = curriculumVitaeRepository.findByCodicePersona(codicePersona);
//			if(cvOpt.isPresent()) {
//				curriculumVitaeRepository.delete(cvOpt.get());
//			}
			
			CurriculumVitae cv = new CurriculumVitae();
			cv.setUtente(utente);
			cv.setCodicePersona(codicePersona);
			cv.setCv(fileData);
			cv.setFilename(file.getOriginalFilename());
			utente.setCv(cv);
			utenteRepository.save(utente);
		} catch (Exception e) {
			throw new FileUploadException("Could not store the file. Error: " + e.getMessage());
		}
	}
	
	public void uploadLogoFornitore(String codiceAzienda, MultipartFile file) {
		try {
			Fornitore fornitore = fornitoreRepository.findById(codiceAzienda).orElseThrow();
			LogoAzienda logo = setLogoAzienda(fornitore, file);
			fornitore.setLogo(logo);
			fornitoreRepository.save(fornitore);
		} catch (Exception e) {
			throw new FileUploadException("Could not store the file. Error: " + e.getMessage());
		}
	}
	
	public void uploadLogoCliente(String codiceAzienda, MultipartFile file) {
		try {
			Cliente cliente = clienteRepository.findByCodiceAzienda(codiceAzienda).orElseThrow();
			LogoAzienda logo = setLogoAzienda(cliente, file);
			cliente.setLogo(logo);
			clienteRepository.save(cliente);
		} catch (Exception e) {
			throw new FileUploadException("Could not store the file. Error: " + e.getMessage());
		}
	}
	
	private LogoAzienda setLogoAzienda(Azienda azienda, MultipartFile file) {
		try {
			String filepath = extracFilename(azienda);
			byte[] fileData = IOUtils.toByteArray(file.getInputStream());
			
			LogoAzienda logo = new LogoAzienda();
			logo.setAzienda(azienda);
			logo.setCodiceAzienda(azienda.getCodiceAzienda());
			logo.setLogo(fileData);
			logo.setFilename(file.getOriginalFilename());
			
			return logo;
		} catch (Exception e) {
			throw new FileUploadException("Could not store the file. Error: " + e.getMessage());
		}
	}
	
	
	/**
	 * upload di un'immagine del profilo
	 * @param codicePersona
	 * @param file
	 */
	public void uploadProfileImage(String codicePersona, MultipartFile file) {
		try {
			Utente utente = utenteRepository.findById(codicePersona).orElseThrow();
			String filepath = extractFilename(codicePersona, utente);
			byte[] fileData = IOUtils.toByteArray(file.getInputStream());
			
			ProfileImage image = new ProfileImage();
			image.setUtente(utente);
			image.setCodicePersona(codicePersona);
			image.setImage(fileData);
			image.setFilename(file.getOriginalFilename());
			utente.setImage(image);;
			utenteRepository.save(utente);
		} catch (Exception e) {
			throw new FileUploadException("Could not store the file. Error: " + e.getMessage());
		}
	}
	
	/**
	 * estrazione del nome del file
	 * @param codicePersona
	 * @param utente
	 * @return
	 */
	private String extractFilename(String codicePersona, Utente utente) {
		String filepath = utente.getCognome() + "-" + utente.getNome() + "-" + codicePersona;
		return Utils.removeAllSpaces(filepath).trim();
	}
	
	private String extracFilename(Azienda azienda) {
		String filepath = azienda.getRagioneSociale() + "-" + azienda.getCodiceAzienda();
		return Utils.removeAllSpaces(filepath).trim();
	}
	
	/**
	 * lettura di un curriculum
	 * @param codicePersona
	 * @return
	 */
	public CurriculumVitae getCurriculum(String codicePersona) {
		try {
			return utenteRepository.getById(codicePersona).getCv();
		} catch (Exception e) {
			throw new FileDownloadException("Error: " + e.getMessage());
		}
	}
	
	public LogoAzienda getLogoCliente(String codiceAzienda) {
		try {
			return clienteRepository.getById(codiceAzienda).getLogo();
		} catch (Exception e) {
			throw new FileDownloadException("Error: " + e.getMessage());
		}
	}
	
	public LogoAzienda getLogoFornitore(String codiceAzienda) {
		try {
			return fornitoreRepository.getById(codiceAzienda).getLogo();
		} catch (Exception e) {
			throw new FileDownloadException("Error: " + e.getMessage());
		}
	}
	
	/**
	 * lettrua di un'immagine del profilo
	 * @param codicePersona
	 * @return
	 */
	public ProfileImage getProfileImage(String codicePersona) {
		try {
			return utenteRepository.getById(codicePersona).getImage();
		} catch (Exception e) {
			throw new FileDownloadException("Error: " + e.getMessage());
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(loadArchivioFolder().toFile());
	}

	public Stream<Path> loadAll() {
		try {
			Path archivio = loadArchivioFolder();
			return Files.walk(archivio, 1).filter(path -> !path.equals(archivio)).map(archivio::relativize);
		} catch (IOException e) {
			throw new FileDownloadException("Could not load the files!");
		}
	}

	private Path loadArchivioFolder() {
		return Paths.get(applicationProperties.getCurriculumDiskDir());
	}

}
