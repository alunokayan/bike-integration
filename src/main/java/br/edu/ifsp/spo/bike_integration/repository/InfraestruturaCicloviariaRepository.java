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

	@Query(value = "SELECT DISTINCT i FROM InfraestruturaCicloviaria i " + "JOIN i.trechos t "
			+ "WHERE ST_Distance_Sphere(POINT(t.latitude, t.longitude), POINT(:latitude, :longitude)) <= :raio")
	List<InfraestruturaCicloviaria> findInfraestruturasProximasByLocation(@Param("latitude") double latitude,
			@Param("longitude") double longitude, @Param("raio") double raio);

}
