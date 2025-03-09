package br.edu.ifsp.spo.bike_integration.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ifsp.spo.bike_integration.context.SpringContext;
import br.edu.ifsp.spo.bike_integration.exception.BikeException;

public interface ObjectMapperUtils {

	Logger LOGGER = LoggerFactory.getLogger(ObjectMapperUtils.class);

	/**
	 *
	 * toJsonString
	 *
	 *
	 */
	public static String toJsonString(Object object) throws BikeException {
		return toJsonString(SpringContext.getBean(ObjectMapper.class), object);
	}

	public static String toJsonString(ObjectMapper objectMapper, Object object) throws BikeException {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			LOGGER.error("Error on toJsonString: ", e);
			return null;
		}
	}

	/**
	 *
	 * toPojo
	 *
	 *
	 */
	public static <T> T toPojo(String jsonString, Class<T> clazz) throws BikeException {
		return toPojo(SpringContext.getBean(ObjectMapper.class), jsonString, clazz);
	}

	public static <T> T toPojo(ObjectMapper objectMapper, String jsonString, Class<T> clazz) throws BikeException {
		try {
			return objectMapper.readValue(jsonString, clazz);
		} catch (Exception e) {
			LOGGER.error("Error on toPojo: ", e);
			return null;
		}
	}

}