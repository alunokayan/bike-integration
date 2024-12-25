package br.edu.ifsp.spo.bike_integration.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.spo.bike_integration.model.TipoEvento;

public interface TipoEventoRepository extends JpaRepository<TipoEvento, Long> {

	Optional<TipoEvento> findByNome(String nome);
	
	List<TipoEvento> findAll();
}
