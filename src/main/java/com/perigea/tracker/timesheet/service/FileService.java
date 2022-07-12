package com.perigea.tracker.timesheet.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
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
import com.perigea.tracker.timesheet.entity.CurriculumVitae;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.repository.CurriculumVitaeRepository;
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
	private CurriculumVitaeRepository curriculumVitaeRepository;
	
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
			String filepath = extractCurriculumFilename(codicePersona, utente);
			
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
			Optional<CurriculumVitae> cvOpt = curriculumVitaeRepository.findByCodicePersona(codicePersona);
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
	
	/**
	 * estrazione del nome del file
	 * @param codicePersona
	 * @param utente
	 * @return
	 */
	private String extractCurriculumFilename(String codicePersona, Utente utente) {
		String filepath = utente.getCognome() + "-" + utente.getNome() + "-" + codicePersona;
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
