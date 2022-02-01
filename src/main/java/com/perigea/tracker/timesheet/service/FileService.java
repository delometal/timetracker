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
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.perigea.tracker.timesheet.configuration.ApplicationProperties;
import com.perigea.tracker.timesheet.entity.Anagrafica;
import com.perigea.tracker.timesheet.entity.CurriculumVitae;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.exception.FileUploadException;
import com.perigea.tracker.timesheet.repository.UtenteRepository;
import com.perigea.tracker.timesheet.utility.TSUtils;

/**
 * TODO rendere opzionale la peristenza su filesystem
 * TODO estrarre nome del file o posizionare nome in tabella
 *
 */
@Service
public class FileService {

	@Autowired
	private Logger logger;
	
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Autowired
	private UtenteRepository utenteRepository;
	
	@PostConstruct
	public void init() {
		try {
			Path archivio = Paths.get(applicationProperties.getUploadDirectory());
			if(!Files.exists(archivio, LinkOption.values())) {
				Files.createDirectory(archivio);
				logger.info(String.format("Archivio folder created in: %s ", archivio.toAbsolutePath()));
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	public void uploadCurriculum(String codicePersona, MultipartFile file) {
		try {
			Utente utente = utenteRepository.getById(codicePersona);
			Anagrafica anagrafica = utente.getAnagrafica();
			String filepath = extractCurriculumFilename(codicePersona, anagrafica);
			Path archivio = loadArchivioFolder();
			Path childPath = archivio.resolve(filepath);
			if(!Files.exists(childPath, LinkOption.values())) {
				Files.createDirectory(childPath);
			}
			if(Files.exists(childPath.resolve(file.getOriginalFilename()), LinkOption.values())) {
				Files.delete(childPath.resolve(file.getOriginalFilename()));
			}
			
			Files.copy(file.getInputStream(), childPath.resolve(file.getOriginalFilename()));
			
			byte[] fileData = IOUtils.toByteArray(file.getInputStream());
			CurriculumVitae cv = new CurriculumVitae();
			cv.setCodicePersona(codicePersona);
			cv.setCv(fileData);
			cv.setFilename(file.getOriginalFilename());
			utente.getAnagrafica().setCv(cv);
			utenteRepository.save(utente);
			
		} catch (Exception e) {
			throw new FileUploadException("Could not store the file. Error: " + e.getMessage());
		}
	}

	private String extractCurriculumFilename(String codicePersona, Anagrafica anagrafica) {
		String filepath = anagrafica.getCognome() + "-" + anagrafica.getNome() + "-" + codicePersona;
		return TSUtils.removeAllSpaces(filepath).trim();
	}

	public CurriculumVitae getCurriculum(String codicePersona) {
		try {
			Utente utente = utenteRepository.getById(codicePersona);
			return utente.getAnagrafica().getCv();
		} catch (Exception e) {
			throw new RuntimeException("Error: " + e.getMessage());
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
			throw new RuntimeException("Could not load the files!");
		}
	}

	private Path loadArchivioFolder() {
		return Paths.get(applicationProperties.getUploadDirectory());
	}

}
