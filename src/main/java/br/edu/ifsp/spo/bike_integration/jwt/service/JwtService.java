package br.edu.ifsp.spo.bike_integration.jwt.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import br.edu.ifsp.spo.bike_integration.dto.JwtConfigDTO;
import br.edu.ifsp.spo.bike_integration.dto.JwtUserDTO;
import br.edu.ifsp.spo.bike_integration.exception.BikeIntegrationCustomException;
import br.edu.ifsp.spo.bike_integration.service.SessaoService;
import br.edu.ifsp.spo.bike_integration.service.UsuarioService;
import br.edu.ifsp.spo.bike_integration.util.ObjectMapperUtils;

@Service
public class JwtService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);

	public static final String DEFAULT_NULL_USER_MESSAGE = "Usuário inválido";

	public static final String DEFAULT_NULL_TOKEN_MESSAGE = "Token inválido ou expirado";

	@Autowired
	private JwtConfigDTO config;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private SessaoService sessaoService;

	public String create(JwtUserDTO subject) throws BikeIntegrationCustomException {
		this.validateUser(subject);
		return JWT.create().withSubject(ObjectMapperUtils.toJsonString(subject)).withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + (this.config.expiration())))
				.sign(this.createAlgorithm());
	}

	public void validateUser(JwtUserDTO subject) throws BikeIntegrationCustomException {
		if (!this.isValidUser(subject)) {
			throw new BikeIntegrationCustomException(DEFAULT_NULL_USER_MESSAGE, HttpStatus.FORBIDDEN);
		}
	}

	public boolean isValidUser(JwtUserDTO subject) {
		try {
			return subject != null
					&& StringUtils.isNotBlank(subject.nickname())
					&& StringUtils.isNotBlank(subject.email())
					&& StringUtils.isNotBlank(subject.password())
					&& StringUtils.isNotBlank(subject.role())
					&& this.usuarioService.loadUsuarioByEmail(subject.email()) != null;
		} catch (Exception e) {
			LOGGER.debug("Error on isValidUser: ", e);
			return false;
		}
	}

	public void validate(String token) throws BikeIntegrationCustomException {
		if (!this.isValid(token)) {
			throw new BikeIntegrationCustomException(DEFAULT_NULL_TOKEN_MESSAGE, HttpStatus.FORBIDDEN);
		}
	}

	public boolean isValid(String token) {
		try {
			JwtUserDTO subject = this.getSubject(token);
			return subject != null
					&& StringUtils.isNotBlank(subject.nickname())
					&& StringUtils.isNotBlank(subject.email())
					&& StringUtils.isNotBlank(subject.password())
					&& StringUtils.isNotBlank(subject.role())
					&& this.sessaoService.isValid(token);
		} catch (Exception e) {
			LOGGER.debug("Error on isValid: ", e);
			return false;
		}
	}

	public boolean isValidSecretKey(String secretKey) {
		return StringUtils.isNotBlank(secretKey) && secretKey.equals(this.config.secretKey());
	}

	public JwtUserDTO getSubject(String token) {
		try {
			return ObjectMapperUtils.toPojo(this.createDecoded(token).getSubject(), JwtUserDTO.class);
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

	private JWTVerifier createVerifier() throws BikeIntegrationCustomException {
		return JWT.require(this.createAlgorithm()).build();
	}

	private Algorithm createAlgorithm() throws BikeIntegrationCustomException {
		return Algorithm.HMAC512(this.config.secretKey().getBytes());
	}

}