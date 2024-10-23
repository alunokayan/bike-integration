package br.edu.ifsp.spo.bike_integration.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifsp.spo.bike_integration.model.WebServiceToken;

public interface WebServiceTokenRepository extends JpaRepository<WebServiceToken, Long> {
    Optional<WebServiceToken> findByToken(String token);
    
    List<WebServiceToken> findAllByValid(Boolean valid);

    // Método para encontrar o último token criado
    @Query("SELECT w FROM WebServiceToken w ORDER BY w.id DESC")
    Optional<WebServiceToken> findLastCreated();
}