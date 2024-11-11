package br.edu.ifsp.spo.bike_integration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.spo.bike_integration.model.Senha;

public interface SenhaRepository extends JpaRepository<Senha, Integer>{
	
}
