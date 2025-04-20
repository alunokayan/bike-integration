package br.edu.ifsp.spo.bike_integration.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.edu.ifsp.spo.bike_integration.service.TokenService;

@Component
public class CleanUpExpiredToken {
	@Autowired
	private TokenService tokenService;

	// Remove tokens expirados a cada hora
	@Scheduled(fixedRateString = "#{T(java.util.concurrent.TimeUnit).HOURS.toMillis(1)}")
	public void cleanUpExpiredTokens() {
		tokenService.removeAll(tokenService.listAllExpiredTokens());
	}
}
