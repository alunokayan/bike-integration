package br.edu.ifsp.spo.bike_integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.spo.bike_integration.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long>{
	
	Optional<Token> findByTokenGerado(String tokenGerado);
}
