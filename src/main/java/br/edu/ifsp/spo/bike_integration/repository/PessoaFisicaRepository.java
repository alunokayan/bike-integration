package br.edu.ifsp.spo.bike_integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.spo.bike_integration.model.PessoaFisica;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Integer>{
	Optional<PessoaFisica> findByCpf(String cpf);
}
