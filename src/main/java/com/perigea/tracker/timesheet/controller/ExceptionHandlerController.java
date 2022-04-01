package com.perigea.tracker.timesheet.controller;

import com.perigea.tracker.commons.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.commons.enums.ResponseType;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(ClienteException.class)
	public final ResponseEntity<?> handleClientException(ClienteException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		ResponseDto<GenericError> errorDto = ResponseDto.<GenericError>builder().code(HttpStatus.BAD_REQUEST.value()).type(ResponseType.ERROR).data(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}
	
	@ExceptionHandler(URIException.class)
	public final ResponseEntity<?> handleClientException(URIException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		ResponseDto<GenericError> errorDto = ResponseDto.<GenericError>builder().code(HttpStatus.BAD_REQUEST.value()).type(ResponseType.ERROR).data(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

	@ExceptionHandler(CommessaException.class)
	public final ResponseEntity<?> handleCommessaException(CommessaException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		ResponseDto<GenericError> errorDto = ResponseDto.<GenericError>builder().code(HttpStatus.BAD_REQUEST.value()).type(ResponseType.ERROR).data(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

	@ExceptionHandler(RuoloException.class)
	public final ResponseEntity<?> handleRoleException(RuoloException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		ResponseDto<GenericError> errorDto = ResponseDto.<GenericError>builder().code(HttpStatus.BAD_REQUEST.value()).type(ResponseType.ERROR).data(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

	@ExceptionHandler(AvvisoBachecaException.class)
	public final ResponseEntity<?> handleAvvisoException(AvvisoBachecaException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		ResponseDto<GenericError> errorDto = ResponseDto.<GenericError>builder().code(HttpStatus.BAD_REQUEST.value()).type(ResponseType.ERROR).data(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

	@ExceptionHandler(TimesheetException.class)
	public final ResponseEntity<?> handleTimeSsheetException(TimesheetException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		ResponseDto<GenericError> errorDto = ResponseDto.<GenericError>builder().code(HttpStatus.BAD_REQUEST.value()).type(ResponseType.ERROR).data(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

	@ExceptionHandler(UtenteException.class)
	public final ResponseEntity<?> handleUserException(UtenteException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		ResponseDto<GenericError> errorDto = ResponseDto.<GenericError>builder().code(HttpStatus.BAD_REQUEST.value()).type(ResponseType.ERROR).data(eObject).build();
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
		ResponseDto<GenericError> errorDto = ResponseDto.<GenericError>builder().code(HttpStatus.BAD_REQUEST.value()).type(ResponseType.ERROR).data(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

	@ExceptionHandler(CentroDiCostoException.class)
	public final ResponseEntity<?> handleCentroDiCostoException(CentroDiCostoException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		ResponseDto<GenericError> errorDto = ResponseDto.<GenericError>builder().code(HttpStatus.BAD_REQUEST.value()).type(ResponseType.ERROR).data(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

	@ExceptionHandler(GruppoException.class)
	public final ResponseEntity<?> handleGruppoException(GruppoException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		ResponseDto<GenericError> errorDto = ResponseDto.<GenericError>builder().code(HttpStatus.BAD_REQUEST.value()).type(ResponseType.ERROR).data(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

	@ExceptionHandler(FileUploadException.class)
	public ResponseEntity<?> handleMaxSizeException(FileUploadException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		ResponseDto<GenericError> errorDto = ResponseDto.<GenericError>builder().code(HttpStatus.BAD_REQUEST.value()).type(ResponseType.ERROR).data(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<?> handleEntityNonFoundException(RuntimeException ex) {
		GenericError eObject = new GenericError();
		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		ResponseDto<GenericError> errorDto = ResponseDto.<GenericError>builder().code(HttpStatus.BAD_REQUEST.value()).type(ResponseType.ERROR).data(eObject).build();
		return ResponseEntity.badRequest().body(errorDto);
	}

}
