package br.edu.ifsp.spo.bike_integration.util;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.edu.ifsp.spo.bike_integration.response.ErrorResponse;

public interface ResponseUtil {

	public static ResponseEntity<Object> createResponse(ErrorResponse errorResponse, HttpStatus badRequest) {
		return new ResponseEntity<>(createMap(errorResponse.getMessage(), badRequest), badRequest);
	}

	/*
	 * PRIVATE METHODS
	 */

	private static Map<String, Object> createMap(String message, HttpStatus status) {
		return Map.of("status", status.value(), "timestamp", LocalDateTime.now(), "message", reduceMessage(message));
	}

	private static String reduceMessage(String message) {
		return message.substring(0, 100).concat("...");
	}
}
