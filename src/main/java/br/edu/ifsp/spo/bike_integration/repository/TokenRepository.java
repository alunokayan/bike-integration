package br.edu.ifsp.spo.bike_integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifsp.spo.bike_integration.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

	Optional<Token> findByTokenGerado(String tokenGerado);

	@Query(value = "SELECT * FROM token t WHERE t.id_usuario = :idUsuario ORDER BY t.dt_criacao DESC LIMIT 1", nativeQuery = true)
	Optional<Token> findLastTokenByUsuarioId(@Param("idUsuario") Long idUsuario);
}
