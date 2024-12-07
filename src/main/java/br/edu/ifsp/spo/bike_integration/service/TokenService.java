package br.edu.ifsp.spo.bike_integration.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.model.Token;
import br.edu.ifsp.spo.bike_integration.model.Usuario;
import br.edu.ifsp.spo.bike_integration.repository.TokenRepository;

@Service
public class TokenService {

	@Autowired
	private TokenRepository tokenRepository;

	public void generateToken(Usuario usuario) {
		Token token = new Token();
		token.setDtCriacao(new Date());
		token.setDtExpiracao(new Date(System.currentTimeMillis() + 6 * 60000));
		token.setTokenGerado(this.generateToken());
		token.setUsuario(usuario);
		tokenRepository.save(token);
	}

	public Token getToken(String tokenValue) {
		return tokenRepository.findByTokenGerado(tokenValue).orElse(null);
	}

	public Boolean validToken(String tokenValue) {
		Token token = this.getToken(tokenValue);
		return token != null && token.getDtExpiracao().after(new Date());
	}

	/*
	 * PRIVATE METHODS
	 */

	// Gera toke de 4 digitos
	private String generateToken() {
		return UUID.randomUUID().toString().substring(0, 4);
	}
}
