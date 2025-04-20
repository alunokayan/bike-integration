package br.edu.ifsp.spo.bike_integration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.spo.bike_integration.model.TipoProblema;

public interface TipoProblemaRepository extends JpaRepository<TipoProblema, Long> {

}
