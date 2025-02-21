package br.edu.ifsp.spo.bike_integration.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifsp.spo.bike_integration.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

	Optional<Token> findByTokenGerado(String tokenGerado);

	@Query(value = "SELECT * FROM token t WHERE t.email = :email ORDER BY t.dt_criacao DESC LIMIT 1", nativeQuery = true)
	Optional<Token> findLastTokenByEmail(@Param("email") String email);

	@Query(value = "SELECT * FROM token t WHERE t.email = :email", nativeQuery = true)
	List<Token> findByEmail(@Param("email") String email);

	@Query(value = "SELECT * FROM token t WHERE t.dt_expiracao < now()", nativeQuery = true)
	List<Token> findAllExpiredTokens();
}
