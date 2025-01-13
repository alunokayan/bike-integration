package br.edu.ifsp.spo.bike_integration.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifsp.spo.bike_integration.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

	String NATIVE_QUERY_FOR_LIST_FILTER = "SELECT e.* FROM evento e "
			+ "INNER JOIN tipo_evento te ON e.id_tipo_evento = te.id "
			+ "WHERE (:nome IS NULL OR LOWER(e.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) "
			+ "AND (:descricao IS NULL OR LOWER(e.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))) "
			+ "AND (:data IS NULL OR DATE(e.data) = DATE(:data)) "
			+ "AND (:cidade IS NULL OR LOWER(JSON_EXTRACT(e.endereco, '$.cidade')) LIKE LOWER(CONCAT('%', :cidade, '%'))) "
			+ "AND (:estado IS NULL OR LOWER(JSON_EXTRACT(e.endereco, '$.estado')) LIKE LOWER(CONCAT('%', :estado, '%'))) "
			+ "AND (:faixaKm IS NULL OR te.faixa_km = :faixaKm) "
			+ "AND (:nivelHabilidade IS NULL OR te.id_nivel_habilidade = :nivelHabilidade) "
			+ "AND (:gratuito IS NULL OR te.gratuito = :gratuito) "
			+ "ORDER BY e.data DESC LIMIT :limit OFFSET :offset";

	String COUNT_QUERY_FOR_LIST_FILTER = "SELECT COUNT(e.id) FROM evento e "
			+ "INNER JOIN tipo_evento te ON e.id_tipo_evento = te.id "
			+ "WHERE (:nome IS NULL OR LOWER(e.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) "
			+ "AND (:descricao IS NULL OR LOWER(e.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))) "
			+ "AND (:data IS NULL OR DATE(e.data) = DATE(:data)) "
			+ "AND (:cidade IS NULL OR LOWER(JSON_EXTRACT(e.endereco, '$.cidade')) LIKE LOWER(CONCAT('%', :cidade, '%'))) "
			+ "AND (:estado IS NULL OR LOWER(JSON_EXTRACT(e.endereco, '$.estado')) LIKE LOWER(CONCAT('%', :estado, '%'))) "
			+ "AND (:faixaKm IS NULL OR te.faixa_km = :faixaKm) "
			+ "AND (:nivelHabilidade IS NULL OR te.id_nivel_habilidade = :nivelHabilidade) "
			+ "AND (:gratuito IS NULL OR te.gratuito = :gratuito)";

	Optional<Evento> findById(Long id);

	List<Evento> findAll();

	@Query(value = NATIVE_QUERY_FOR_LIST_FILTER, nativeQuery = true)
	List<Evento> findAll(@Param("limit") Long limit, @Param("offset") Long offset, @Param("nome") String nome,
			@Param("descricao") String descricao, @Param("data") String data, @Param("cidade") String cidade,
			@Param("estado") String estado, @Param("faixaKm") Long faixaKm,
			@Param("nivelHabilidade") Long nivelHabilidade, @Param("gratuito") Boolean gratuito);

	@Query(value = COUNT_QUERY_FOR_LIST_FILTER, nativeQuery = true)
	Long countAll(@Param("nome") String nome, @Param("descricao") String descricao, @Param("data") String data,
			@Param("cidade") String cidade, @Param("estado") String estado, @Param("faixaKm") Long faixaKm,
			@Param("nivelHabilidade") Long nivelHabilidade, @Param("gratuito") Boolean gratuito);

	@Query(value = "SELECT * FROM evento e WHERE ST_Distance_Sphere("
			+ "POINT(CAST(JSON_EXTRACT(e.endereco, '$.longitude') AS DECIMAL(10,8)), "
			+ "CAST(JSON_EXTRACT(e.endereco, '$.latitude') AS DECIMAL(10,8))), "
			+ "POINT(:longitude, :latitude)) <= :raio", nativeQuery = true)
	List<Evento> findEventosProximosByLocation(@Param("latitude") double latitude, @Param("longitude") double longitude,
			@Param("raio") double raio);
}
