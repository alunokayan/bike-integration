package br.edu.ifsp.spo.bike_integration.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifsp.spo.bike_integration.model.InfraestruturaCicloviaria;

public interface InfraestruturaCicloviariaRepository extends JpaRepository<InfraestruturaCicloviaria, Long> {

	Optional<InfraestruturaCicloviaria> findById(Long id);

	List<InfraestruturaCicloviaria> findAll();

	@Query("SELECT ic " + "FROM InfraestruturaCicloviaria ic " + "JOIN ic.trechos t "
			+ "WHERE ST_Distance_Sphere(POINT(t.latitude, t.longitude), POINT(:latitude, :longitude)) <= :raio "
			+ "GROUP BY ic.id "
			+ "ORDER BY MIN(CAST(ST_Distance_Sphere(POINT(t.latitude, t.longitude), POINT(:latitude, :longitude)) AS double)) ASC")
	List<InfraestruturaCicloviaria> findInfraestruturasProximasByLocation(@Param("latitude") double latitude,
			@Param("longitude") double longitude, @Param("raio") double raio);

	@Query("UPDATE InfraestruturaCicloviaria ic SET ic.nota = :nota WHERE ic.id = :idInfraestruturaCicloviaria")
	void atualizarNota(@Param("idInfraestruturaCicloviaria") Long idInfraestruturaCicloviaria,
			@Param("nota") Integer nota);

}
