package br.edu.ifsp.spo.bike_integration.exception;

public class CryptoException extends Exception {
	private static final long serialVersionUID = 6315131013398328579L;

	public CryptoException(String message, Throwable cause) {
		super(message, cause);
	}
}
