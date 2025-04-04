package br.edu.ifsp.spo.bike_integration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifsp.spo.bike_integration.model.Problema;
import br.edu.ifsp.spo.bike_integration.model.TipoProblema;
import br.edu.ifsp.spo.bike_integration.model.Trecho;

public interface ProblemaRepository extends JpaRepository<Problema, Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Problema p WHERE p.trecho = :trecho AND p.tipoProblema = :tipoProblema")
    boolean existsByTrechoAndTipoProblema(Trecho trecho, TipoProblema tipoProblema);

    @Query("SELECT p FROM Problema p JOIN p.trecho t WHERE ST_Distance_Sphere(POINT(t.latitude, t.longitude), POINT(:latitude, :longitude)) <= :raio")
    List<Problema> findProblemasProximosByLocation(@Param("latitude") double latitude,
            @Param("longitude") double longitude, @Param("raio") double raio);

}
