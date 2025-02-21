package br.edu.ifsp.spo.bike_integration.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifsp.spo.bike_integration.model.ConfiguracaoApiExterna;

public interface ConfiguracaoApiExternaRepository extends JpaRepository<ConfiguracaoApiExterna, Long> {

	Optional<ConfiguracaoApiExterna> findByNome(String nome);

	@Query("SELECT c FROM ConfiguracaoApiExterna c WHERE c.nome like %:nome%")
	List<ConfiguracaoApiExterna> findAllByNome(String nome);

	Optional<ConfiguracaoApiExterna> findById(Long id);

	List<ConfiguracaoApiExterna> findAll();
}
