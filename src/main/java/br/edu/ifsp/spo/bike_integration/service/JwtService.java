package br.edu.ifsp.spo.bike_integration.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import br.edu.ifsp.spo.bike_integration.dto.JwtConfigDTO;
import br.edu.ifsp.spo.bike_integration.dto.JwtSubjectDTO;
import br.edu.ifsp.spo.bike_integration.exception.BikeException;
import br.edu.ifsp.spo.bike_integration.util.ObjectMapperUtils;

@Service
public class JwtService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);

	public static final String DEFAULT_NULL_SUBJECT_MESSAGE = "Token inválido ou expirado";

	@Value("${jwt.subject.access-key}")
	private String subjectAccessKey;

	@Value("${jwt.subject.secret-key}")
	private String subjectSecretKey;

	private final JwtConfigDTO config;

	public JwtService(final JwtConfigDTO config) {
		this.config = config;
	}

	public String create(JwtSubjectDTO subject) throws BikeException {
		this.validateSubject(subject);
		return JWT.create().withSubject(ObjectMapperUtils.toJsonString(subject)).withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + (this.config.expiration())))
				.sign(this.createAlgorithm());
	}

	public void validateSubject(JwtSubjectDTO subject) throws BikeException {
		if (!this.isValidSubject(subject)) {
			throw new BikeException(DEFAULT_NULL_SUBJECT_MESSAGE, HttpStatus.FORBIDDEN);
		}
	}

	public boolean isValidSubject(JwtSubjectDTO subject) {
		try {
			return subject != null && StringUtils.isNotBlank(subject.accessKey())
					&& StringUtils.isNotBlank(subject.secretKey()) && subject.accessKey().equals(this.subjectAccessKey)
					&& subject.secretKey().equals(this.subjectSecretKey);
		} catch (Exception e) {
			LOGGER.debug("Error on isValidSubject: ", e);
			return false;
		}
	}

	public void validate(String token) throws BikeException {
		if (!this.isValid(token)) {
			throw new BikeException(DEFAULT_NULL_SUBJECT_MESSAGE, HttpStatus.FORBIDDEN);
		}
	}

	public boolean isValid(String token) {
		try {
			JwtSubjectDTO subject = this.getSubject(token);
			return subject != null && StringUtils.isNotBlank(subject.accessKey())
					&& StringUtils.isNotBlank(subject.secretKey());
		} catch (Exception e) {
			LOGGER.debug("Error on isValid: ", e);
			return false;
		}
	}

	public JwtSubjectDTO getSubject(String token) {
		try {
			return ObjectMapperUtils.toPojo(this.createDecoded(token).getSubject(), JwtSubjectDTO.class);
		} catch (Exception e) {
			LOGGER.debug("Error on getSubject: ", e);
			return null;
		}
	}

	/**
	 *
	 * PRIVATE's
	 *
	 *
	 */
	private DecodedJWT createDecoded(String token) throws JWTVerificationException {
		return this.createVerifier().verify(token);
	}

	private JWTVerifier createVerifier() throws BikeException {
		return JWT.require(this.createAlgorithm()).build();
	}

	private Algorithm createAlgorithm() throws BikeException {
		return Algorithm.HMAC512(this.config.secretKey().getBytes());
	}

}