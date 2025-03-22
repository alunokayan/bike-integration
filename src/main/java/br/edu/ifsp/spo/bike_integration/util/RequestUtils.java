package br.edu.ifsp.spo.bike_integration.util;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

public interface RequestUtils {

	Logger LOGGER = LoggerFactory.getLogger(RequestUtils.class);

	/**
	 *
	 * getBearerToken
	 *
	 *
	 */
	public static Optional<String> getBearerToken(HttpServletRequest req) {
		try {
			final String headerName = "Authorization";
			final String bearerPrefix = "Bearer ";
			Optional<String> optBearerToken = getHeader(req, headerName);
			if (optBearerToken == null || optBearerToken.isEmpty()) {
				return Optional.empty();
			}
			String bearerToken = optBearerToken.get();
			return (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith(bearerPrefix))
					? Optional.ofNullable(bearerToken.substring(bearerPrefix.length()))
					: Optional.empty();
		} catch (Exception e) {
			LOGGER.error("Error on getBearerToken: ", e);
			return Optional.empty();
		}
	}

	/**
	 *
	 * getAccessKey
	 *
	 *
	 */
	public static Optional<String> getAccessKey(HttpServletRequest req) {
		try {
			final String headerName = "X-Access-Key";
			return getHeader(req, headerName);
		} catch (Exception e) {
			LOGGER.error("Error on getAccessKey: ", e);
			return Optional.empty();
		}
	}

	/**
	 *
	 * getHeader
	 *
	 *
	 */
	public static Optional<String> getHeader(HttpServletRequest req, String name) {
		return Optional.ofNullable(req.getHeader(name));
	}

	/**
	 *
	 * getCurrent
	 *
	 *
	 */
	public static HttpServletRequest getCurrent() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return (attributes != null) ? attributes.getRequest() : null;
	}

}