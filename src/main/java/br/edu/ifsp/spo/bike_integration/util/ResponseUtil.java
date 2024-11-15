package br.edu.ifsp.spo.bike_integration.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
	
	private ResponseUtil() {
		
	}

	public static ResponseEntity<Object> createResponse(String message, HttpStatus status) {
		return new ResponseEntity<>(message, status);
	}
	
	public static ResponseEntity<Object> createResponse(HttpStatus status) {
		return new ResponseEntity<>(status);
	}
	
	public static ResponseEntity<Object> createResponse(Object object, HttpStatus status) {
		return new ResponseEntity<>(object, status);
	}
}
