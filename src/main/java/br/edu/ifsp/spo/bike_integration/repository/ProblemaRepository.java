package br.edu.ifsp.spo.bike_integration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifsp.spo.bike_integration.model.Problema;
import br.edu.ifsp.spo.bike_integration.model.TipoProblema;
import br.edu.ifsp.spo.bike_integration.model.Trecho;

public interface ProblemaRepository extends JpaRepository<Problema, Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Problema p WHERE p.trecho = :trecho AND p.tipoProblema = :tipoProblema")
    boolean existsByTrechoAndTipoProblema(Trecho trecho, TipoProblema tipoProblema);

}
