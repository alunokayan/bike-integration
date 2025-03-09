package br.edu.ifsp.spo.bike_integration.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.edu.ifsp.spo.bike_integration.exception.CryptoException;
import br.edu.ifsp.spo.bike_integration.response.ErrorResponse;
import br.edu.ifsp.spo.bike_integration.util.ResponseUtil;

@ControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
		logger.error("IllegalArgumentException caught: ", ex);
		return ResponseUtil.createResponse(new ErrorResponse("Erro: \n" + ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CryptoException.class)
	public ResponseEntity<Object> handleCryptoException(CryptoException ex, WebRequest request) {
		logger.error("CryptoException caught: ", ex);
		return ResponseUtil.createResponse(new ErrorResponse("Erro de criptografia: \n" + ex.getMessage()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
		logger.error("NotFoundException caught: ", ex);
		return ResponseUtil.createResponse(new ErrorResponse("Erro: \n" + "não encontrado."), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(
			org.springframework.security.access.AccessDeniedException ex, WebRequest request) {
		logger.error("AccessDeniedException caught: ", ex);
		return ResponseUtil.createResponse(new ErrorResponse("Erro de acesso: \n" + ex.getMessage()),
				HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
		logger.error("General Exception caught: ", ex);
		return ResponseUtil.createResponse(new ErrorResponse("Erro interno: \n" + ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
		logger.error("RuntimeException caught: ", ex);
		return ResponseUtil.createResponse(new ErrorResponse("Erro interno: \n" + ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public ResponseEntity<Object> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex,
			WebRequest request) {
		logger.error("InvalidDataAccessApiUsageException caught: ", ex);
		return ResponseUtil.createResponse(new ErrorResponse("Erro: \n" + ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}