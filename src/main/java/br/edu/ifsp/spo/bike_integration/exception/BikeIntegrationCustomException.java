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
public class BikeIntegrationCustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final HttpStatus DEFAULT_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

	private HttpStatus status;

	public BikeIntegrationCustomException(Throwable throwable) {
		this(throwable, BikeIntegrationCustomException.getStatus(throwable));
	}

	public BikeIntegrationCustomException(Throwable throwable, HttpStatus status) {
		this(null, throwable, status);
	}

	public BikeIntegrationCustomException(String message) {
		this(message, DEFAULT_STATUS);
	}

	public BikeIntegrationCustomException(String message, HttpStatus status) {
		this(message, null, status);
	}

	public BikeIntegrationCustomException(HttpStatus status) {
		this(null, null, status);
	}

	public BikeIntegrationCustomException(String message, Throwable throwable) {
		this(message, throwable, BikeIntegrationCustomException.getStatus(throwable));
	}

	public BikeIntegrationCustomException(String message, Throwable throwable, HttpStatus status) {
		super((StringUtils.isNotBlank(message) ? message : ExceptionUtils.getRootCauseMessage(throwable)),
				ExceptionUtils.getRootCause(throwable));
		this.status = status;
	}

	private static HttpStatus getStatus(Throwable throwable) {
		return (throwable instanceof BikeIntegrationCustomException)
				? ((BikeIntegrationCustomException) throwable).getStatus()
				: DEFAULT_STATUS;
	}

}