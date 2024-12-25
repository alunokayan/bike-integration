package br.edu.ifsp.spo.bike_integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.spo.bike_integration.model.NivelHabilidade;


public interface NivelHabilidadeRepository extends JpaRepository<NivelHabilidade, Long>{
	
	Optional<NivelHabilidade> findByNome(String nome);
}
