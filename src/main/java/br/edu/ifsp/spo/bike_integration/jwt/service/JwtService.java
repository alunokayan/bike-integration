package br.edu.ifsp.spo.bike_integration.jwt.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.edu.ifsp.spo.bike_integration.dto.JwtConfigDTO;
import br.edu.ifsp.spo.bike_integration.dto.JwtUserDTO;
import br.edu.ifsp.spo.bike_integration.exception.BikeIntegrationCustomException;
import br.edu.ifsp.spo.bike_integration.util.ObjectMapperUtils;

@Service
public class JwtService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);

    public static final String DEFAULT_NULL_USER_MESSAGE = "Usuário inválido";

    public static final String DEFAULT_NULL_TOKEN_MESSAGE = "Token inválido ou expirado";

    @Autowired
    private JwtConfigDTO config;

    public String create(JwtUserDTO subject) throws BikeIntegrationCustomException {
        return JWT.create().withSubject(ObjectMapperUtils.toJsonString(subject)).withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + (this.config.expiration())))
                .sign(this.createAlgorithm());
    }

    /**
     *
     * PRIVATE's
     *
     *
     */

    private Algorithm createAlgorithm() throws BikeIntegrationCustomException {
        return Algorithm.HMAC512(this.config.secretKey().getBytes());
    }

}