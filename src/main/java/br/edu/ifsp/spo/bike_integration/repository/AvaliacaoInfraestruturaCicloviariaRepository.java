package br.edu.ifsp.spo.bike_integration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.spo.bike_integration.model.AvaliacaoInfraestruturaCicloviaria;

public interface AvaliacaoInfraestruturaCicloviariaRepository
		extends JpaRepository<AvaliacaoInfraestruturaCicloviaria, Long> {

	List<AvaliacaoInfraestruturaCicloviaria> findAllByInfraestruturaCicloviariaId(Long idInfraestruturaCicloviaria);

}
