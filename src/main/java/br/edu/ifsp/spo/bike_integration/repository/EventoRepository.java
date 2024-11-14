package br.edu.ifsp.spo.bike_integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.spo.bike_integration.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long>{
	Optional<Evento> findByNome(String nome);
}
