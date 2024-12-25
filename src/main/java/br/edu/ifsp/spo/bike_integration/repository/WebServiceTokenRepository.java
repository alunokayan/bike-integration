package br.edu.ifsp.spo.bike_integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.spo.bike_integration.model.WebServiceToken;

public interface WebServiceTokenRepository extends JpaRepository<WebServiceToken, Long> {
    Optional<WebServiceToken> findByToken(String token);
}