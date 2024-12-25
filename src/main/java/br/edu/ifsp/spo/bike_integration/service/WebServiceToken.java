package br.edu.ifsp.spo.bike_integration.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.repository.WebServiceTokenRepository;
import br.edu.ifsp.spo.bike_integration.util.HeaderUtil;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class WebServiceToken {

	@Autowired
	private WebServiceTokenRepository webServiceTokenRepository;
	
	private WebServiceToken() {
	}
	
	public Authentication getAuthentication(HttpServletRequest request) {
    	Authentication retorno = new TestingAuthenticationToken("", "");
    	String token = HeaderUtil.getAccessToken(request);
    	if (StringUtils.isNotBlank(token)) {
    	    retorno.setAuthenticated(isValid(token));
    	}
    	return retorno;
    }

    private boolean isValid(String token) {
		return webServiceTokenRepository.findByToken(token).isPresent();
    }
}