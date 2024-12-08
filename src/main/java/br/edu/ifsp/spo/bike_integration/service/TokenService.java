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

	public Token generateToken(Usuario usuario) {
		return tokenRepository.save(
				Token.builder().dtCriacao(new Date()).dtExpiracao(new Date(System.currentTimeMillis() + 6 * 60000))
						.tokenGerado(this.generateToken()).usuario(usuario).build());
	}

	public Token getToken(String tokenValue) {
		return tokenRepository.findByTokenGerado(tokenValue).orElse(null);
	}

	public Boolean isValidToken(String tokenValue, Long idUsuario) {
		Token token = this.getToken(tokenValue);
		if (token != null && idUsuario.equals(token.getUsuario().getId()) && token.getDtExpiracao().after(new Date())) {
			this.deleteToken(token);
			return true;
		}
		return false;
	}

	public void deleteToken(Token token) {
		tokenRepository.delete(token);
	}

	public Token updateToken(Token token) {
		return tokenRepository.save(token);
	}

	/*
	 * PRIVATE METHODS
	 */

	// Gera toke de 4 digitos
	private String generateToken() {
		return UUID.randomUUID().toString().substring(0, 4);
	}
}
