package br.edu.ifsp.spo.bike_integration.exception;

import java.io.Serial;

public class CryptoException extends Exception {
	@Serial
	private static final long serialVersionUID = 6315131013398328579L;

	public CryptoException(String message, Throwable cause) {
		super(message, cause);
	}
}
