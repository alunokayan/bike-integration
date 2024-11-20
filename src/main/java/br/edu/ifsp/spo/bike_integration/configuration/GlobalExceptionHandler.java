package br.edu.ifsp.spo.bike_integration.configuration;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.edu.ifsp.spo.bike_integration.exception.CryptoException;
import br.edu.ifsp.spo.bike_integration.util.ResponseUtil;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return ResponseUtil.createResponse("Erro: \n" + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CryptoException.class)
    public ResponseEntity<Object> handleCryptoException(CryptoException ex, WebRequest request) {
        return ResponseUtil.createResponse("Erro de criptografia: \n" + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
		return ResponseUtil.createResponse("Erro: \n" + "não encontrado.", HttpStatus.NOT_FOUND);
	}
    
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)	
    public ResponseEntity<Object> handleAccessDeniedException(org.springframework.security.access.AccessDeniedException ex, WebRequest request) {
        return ResponseUtil.createResponse("Erro de acesso: \n" + ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return ResponseUtil.createResponse("Erro interno: \n" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        ex.printStackTrace();
        return ResponseUtil.createResponse("Erro interno: \n" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
