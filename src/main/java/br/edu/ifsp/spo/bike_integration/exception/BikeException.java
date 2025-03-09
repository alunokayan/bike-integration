package br.edu.ifsp.spo.bike_integration.exception;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
public class BikeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final HttpStatus DEFAULT_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

	private HttpStatus status;

	public BikeException(Throwable throwable) {
		this(throwable, BikeException.getStatus(throwable));
	}

	public BikeException(Throwable throwable, HttpStatus status) {
		this(null, throwable, status);
	}

	public BikeException(String message) {
		this(message, DEFAULT_STATUS);
	}

	public BikeException(String message, HttpStatus status) {
		this(message, null, status);
	}

	public BikeException(HttpStatus status) {
		this(null, null, status);
	}

	public BikeException(String message, Throwable throwable) {
		this(message, throwable, BikeException.getStatus(throwable));
	}

	public BikeException(String message, Throwable throwable, HttpStatus status) {
		super((StringUtils.isNotBlank(message) ? message : ExceptionUtils.getRootCauseMessage(throwable)),
				ExceptionUtils.getRootCause(throwable));
		this.status = status;
	}

	private static HttpStatus getStatus(Throwable throwable) {
		return (throwable instanceof BikeException) ? ((BikeException) throwable).getStatus() : DEFAULT_STATUS;
	}

}