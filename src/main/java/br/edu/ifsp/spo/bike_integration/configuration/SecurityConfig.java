package br.edu.ifsp.spo.bike_integration.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import br.edu.ifsp.spo.bike_integration.filter.AuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private AuthenticationFilter tokenFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(requests -> requests
						.requestMatchers(getPublicRequestMatchersAsArray()).permitAll()
						.anyRequest().authenticated())
				.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	// Define os endpoints públicos
	public static List<RequestMatcher> getPublicRequestMatchers() {
		List<RequestMatcher> matchers = new ArrayList<>();
		matchers.add(new RegexRequestMatcher("/v3/api-docs.*", null));
		matchers.add(new RegexRequestMatcher("/swagger.*", null));
		matchers.add(new AntPathRequestMatcher("/"));
		matchers.add(new AntPathRequestMatcher("/home"));
		matchers.add(new AntPathRequestMatcher("/ping"));
		matchers.add(new AntPathRequestMatcher("/version"));
		matchers.add(new AntPathRequestMatcher("/detail"));
		return matchers;
	}

	/**
	 * Retorna os endpoints públicos como um array de RequestMatcher
	 * 
	 * @return
	 */
	public static RequestMatcher[] getPublicRequestMatchersAsArray() {
		return getPublicRequestMatchers().toArray(new RequestMatcher[0]);
	}
}