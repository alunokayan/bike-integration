package br.edu.ifsp.spo.bike_integration.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.edu.ifsp.spo.bike_integration.service.ProblemaService;

@Component
public class CleanUpExpiredProblema {
    @Autowired
    private ProblemaService problemaService;

    // Remove tokens expirados a cada 10 dias
    @Scheduled(fixedRateString = "#{T(java.util.concurrent.TimeUnit).DAYS.toMillis(10)}")
    public void cleanUpExpiredTokens() {
        problemaService.deleteAll(problemaService.listAllExpiredProblemas());
    }

}
