package br.edu.ifsp.spo.bike_integration.util;

import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.edu.ifsp.spo.bike_integration.exception.BikeIntegrationCustomException;
import jakarta.servlet.http.HttpServletResponse;

public interface ResponseUtils {

	Logger LOGGER = LoggerFactory.getLogger(ResponseUtils.class);

	/**
	 *
	 * putJsonOnResponse
	 *
	 *
	 */
	public static void putJsonOnResponse(HttpServletResponse response, Object object, HttpStatus httpStatus)
			throws BikeIntegrationCustomException {
		putJsonOnResponse(response, ObjectMapperUtils.toJsonString(object), httpStatus);
	}

	public static void putJsonOnResponse(HttpServletResponse response, String json, HttpStatus httpStatus)
			throws BikeIntegrationCustomException {
		try {
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(Charset.forName("UTF-8").name());
			if (StringUtils.isNotBlank(json)) {
				response.getWriter().write(json);
			}
			response.setStatus(httpStatus.value());
		} catch (Exception e) {
			throw new BikeIntegrationCustomException("Erro ao escrever resposta JSON", e);
		}
	}

	/**
	 *
	 * getCurrent
	 *
	 *
	 */
	public static HttpServletResponse getCurrent() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return (attributes != null) ? attributes.getResponse() : null;
	}

}