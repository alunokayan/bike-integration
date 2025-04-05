package br.edu.ifsp.spo.bike_integration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifsp.spo.bike_integration.model.AvaliacaoInfraestruturaCicloviaria;

public interface AvaliacaoInfraestruturaCicloviariaRepository
		extends JpaRepository<AvaliacaoInfraestruturaCicloviaria, Long> {

	String NATIVE_QUERY_FOR_LIST_FILTER = "SELECT * FROM avaliacao_infraestrutura_cicloviaria a "
			+ "WHERE a.id_infraestrutura_cicloviaria = :idInfraestruturaCicloviaria "
			+ "AND (:nota IS NULL OR a.nota = :nota) "
			+ "ORDER BY a.dt_criacao DESC LIMIT :limit OFFSET :offset";

	String COUNT_QUERY_FOR_LIST_FILTER = "SELECT COUNT(a.id) FROM avaliacao_infraestrutura_cicloviaria a "
			+ "WHERE a.id_infraestrutura_cicloviaria = :idInfraestruturaCicloviaria "
			+ "AND (:nota IS NULL OR a.nota = :nota)";

	@Query(value = NATIVE_QUERY_FOR_LIST_FILTER, nativeQuery = true)
	List<AvaliacaoInfraestruturaCicloviaria> findAllByInfraestruturaCicloviariaIdAndNota(
			@Param("idInfraestruturaCicloviaria") Long idInfraestruturaCicloviaria,
			@Param("nota") Integer nota, @Param("limit") Long limit, @Param("offset") Long offset);

	@Query(value = COUNT_QUERY_FOR_LIST_FILTER, nativeQuery = true)
	Long countByInfraestruturaCicloviariaIdAndNota(
			@Param("idInfraestruturaCicloviaria") Long idInfraestruturaCicloviaria,
			@Param("nota") Integer nota);

	List<AvaliacaoInfraestruturaCicloviaria> findAllByInfraestruturaCicloviariaId(Long idInfraestruturaCicloviaria);
}
