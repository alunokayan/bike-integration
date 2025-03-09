package br.edu.ifsp.spo.bike_integration.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.edu.ifsp.spo.bike_integration.configuration.WebSecurityConfig;
import br.edu.ifsp.spo.bike_integration.dto.JwtSubjectDTO;
import br.edu.ifsp.spo.bike_integration.dto.StandardErrorDTO;
import br.edu.ifsp.spo.bike_integration.service.JwtService;
import br.edu.ifsp.spo.bike_integration.util.RequestUtils;
import br.edu.ifsp.spo.bike_integration.util.ResponseUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	public AuthenticationFilter(final JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (WebSecurityConfig.isPublic(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		Optional<String> optToken = RequestUtils.getBearerToken(request);
		if (optToken.isEmpty() || !this.jwtService.isValid(optToken.get())) {
			this.putErrorOnResponse(response);
			return;
		}

		JwtSubjectDTO subject = this.jwtService.getSubject(optToken.get());
		if (!this.jwtService.isValidSubject(subject)) {
			this.putErrorOnResponse(response);
			return;
		}

		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(subject.accessKey(), null, new HashSet<>()));
		filterChain.doFilter(request, response);
	}

	private void putErrorOnResponse(HttpServletResponse response) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		ResponseUtils.putJsonOnResponse(response, StandardErrorDTO.builder().status(status.value())
				.message(JwtService.DEFAULT_NULL_SUBJECT_MESSAGE).build(), status);
	}

}