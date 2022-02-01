package com.perigea.tracker.timesheet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.exception.ClienteException;
import com.perigea.tracker.timesheet.exception.CommessaException;
import com.perigea.tracker.timesheet.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.exception.FestivitaException;
import com.perigea.tracker.timesheet.exception.FileUploadException;
import com.perigea.tracker.timesheet.exception.GenericError;
import com.perigea.tracker.timesheet.exception.GruppoException;
import com.perigea.tracker.timesheet.exception.RuoloException;
import com.perigea.tracker.timesheet.exception.TimesheetException;
import com.perigea.tracker.timesheet.exception.UtenteException;
import com.perigea.tracker.timesheet.utility.TSUtils;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(ClienteException.class)
	public final ResponseEntity<?> handleClientException(ClienteException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		GenericWrapperResponse<GenericError> errorDto = GenericWrapperResponse.<GenericError>builder()
				.dataRichiesta(TSUtils.now()).risultato(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

	@ExceptionHandler(CommessaException.class)
	public final ResponseEntity<?> handleCommessaException(CommessaException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		GenericWrapperResponse<GenericError> errorDto = GenericWrapperResponse.<GenericError>builder()
				.dataRichiesta(TSUtils.now()).risultato(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

	@ExceptionHandler(RuoloException.class)
	public final ResponseEntity<?> handleRoleException(RuoloException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		GenericWrapperResponse<GenericError> errorDto = GenericWrapperResponse.<GenericError>builder()
				.dataRichiesta(TSUtils.now()).risultato(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

	@ExceptionHandler(TimesheetException.class)
	public final ResponseEntity<?> handleTimeSsheetException(TimesheetException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		GenericWrapperResponse<GenericError> errorDto = GenericWrapperResponse.<GenericError>builder()
				.dataRichiesta(TSUtils.now()).risultato(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

	@ExceptionHandler(UtenteException.class)
	public final ResponseEntity<?> handleUserException(UtenteException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		GenericWrapperResponse<GenericError> errorDto = GenericWrapperResponse.<GenericError>builder()
				.dataRichiesta(TSUtils.now()).risultato(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);

	}

	@ExceptionHandler(EntityNotFoundException.class)
	public final ResponseEntity<?> handleEntityNonFoundException(EntityNotFoundException ex) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(FestivitaException.class)
	public final ResponseEntity<?> handleFestivitaException(FestivitaException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		GenericWrapperResponse<GenericError> errorDto = GenericWrapperResponse.<GenericError>builder()
				.dataRichiesta(TSUtils.now()).risultato(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

	@ExceptionHandler(GruppoException.class)
	public final ResponseEntity<?> handleGruppoException(GruppoException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		GenericWrapperResponse<GenericError> errorDto = GenericWrapperResponse.<GenericError>builder()
				.dataRichiesta(TSUtils.now()).risultato(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

	@ExceptionHandler(FileUploadException.class)
	public ResponseEntity<?> handleMaxSizeException(FileUploadException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		GenericWrapperResponse<GenericError> errorDto = GenericWrapperResponse.<GenericError>builder()
				.dataRichiesta(TSUtils.now()).risultato(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<?> handleEntityNonFoundException(RuntimeException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		GenericWrapperResponse<GenericError> errorDto = GenericWrapperResponse.<GenericError>builder()
				.dataRichiesta(TSUtils.now()).risultato(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

}
