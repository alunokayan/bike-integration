package br.edu.ifsp.spo.bike_integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.spo.bike_integration.model.ConfiguracaoApiExterna;

public interface ConfiguracaoApiExternaRepository extends JpaRepository<ConfiguracaoApiExterna, Long>{
	Optional<ConfiguracaoApiExterna> findByNome(String nome);
}
