package br.edu.ifsp.spo.bike_integration.interceptor;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.edu.ifsp.spo.bike_integration.annotation.BearerAuthentication;
import br.edu.ifsp.spo.bike_integration.annotation.JwtSecretKey;
import br.edu.ifsp.spo.bike_integration.annotation.Role;
import br.edu.ifsp.spo.bike_integration.configuration.WebSecurityConfig;
import br.edu.ifsp.spo.bike_integration.dto.JwtUserDTO;
import br.edu.ifsp.spo.bike_integration.dto.StandardErrorDTO;
import br.edu.ifsp.spo.bike_integration.hardcode.RoleType;
import br.edu.ifsp.spo.bike_integration.jwt.service.JwtService;
import br.edu.ifsp.spo.bike_integration.util.RequestUtils;
import br.edu.ifsp.spo.bike_integration.util.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException {
		if (WebSecurityConfig.isPublic(request)) {
			return true;
		}

		if (handler instanceof HandlerMethod method) {
			boolean isBearer = method.hasMethodAnnotation(BearerAuthentication.class);
			boolean isSecretKey = method.hasMethodAnnotation(JwtSecretKey.class);

			RoleType[] roles = method.getMethodAnnotation(Role.class).value();

			if (isBearer) {
				return this.authenticateWithBearer(request, response);
			}
			if (isSecretKey) {
				return this.authenticateWithSecretKey(request, response);
			}
		}
		this.putErrorOnResponse(response);
		return false;
	}

	/*
	 * PRIVATE METHODS
	 */
	private boolean authenticateWithBearer(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Optional<String> optToken = RequestUtils.getBearerToken(request);
		if (optToken.isEmpty() || !this.jwtService.isValid(optToken.get())) {
			this.putErrorOnResponse(response, JwtService.DEFAULT_NULL_TOKEN_MESSAGE);
			return false;
		}
		JwtUserDTO jwsUser = this.jwtService.getSubject(optToken.get());
		if (!this.jwtService.isValidUser(jwsUser)) {
			this.putErrorOnResponse(response);
			return false;
		}
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(jwsUser, null,
				new HashSet<>()));
		return true;
	}

	private boolean authenticateWithSecretKey(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Optional<String> optSecretKey = RequestUtils.getBearerToken(request);
		if (optSecretKey.isEmpty() || !this.jwtService.isValidSecretKey(optSecretKey.get())) {
			this.putErrorOnResponse(response, JwtService.DEFAULT_NULL_TOKEN_MESSAGE);
			return false;
		}
		return true;
	}

	private void putErrorOnResponse(HttpServletResponse response) {
		this.putErrorOnResponse(response, JwtService.DEFAULT_NULL_TOKEN_MESSAGE);
	}

	private void putErrorOnResponse(HttpServletResponse response, String message) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		ResponseUtils.putJsonOnResponse(response, StandardErrorDTO.builder().status(status.value())
				.message(message).build(), status);
	}
}