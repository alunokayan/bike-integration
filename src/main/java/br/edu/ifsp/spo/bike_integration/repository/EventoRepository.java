package br.edu.ifsp.spo.bike_integration.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifsp.spo.bike_integration.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

	Optional<Evento> findById(Long id);

	List<Evento> findAll();

	@Query(value = "SELECT * FROM evento e " + "WHERE ST_Distance_Sphere("
			+ "POINT(CAST(JSON_EXTRACT(e.endereco, '$.longitude') AS DECIMAL(10,8)), "
			+ "CAST(JSON_EXTRACT(e.endereco, '$.latitude') AS DECIMAL(10,8))), "
			+ "POINT(:longitude, :latitude)) <= :raio", nativeQuery = true)
	List<Evento> findEventosProximosByLocation(@Param("latitude") double latitude, @Param("longitude") double longitude,
			@Param("raio") double raio);
}
