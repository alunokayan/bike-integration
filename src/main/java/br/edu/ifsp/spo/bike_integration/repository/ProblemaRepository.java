package br.edu.ifsp.spo.bike_integration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.spo.bike_integration.model.Problema;

public interface ProblemaRepository extends JpaRepository<Problema, Long> {

}
