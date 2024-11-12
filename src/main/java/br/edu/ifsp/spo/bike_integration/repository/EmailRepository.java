package br.edu.ifsp.spo.bike_integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.spo.bike_integration.model.Email;

public interface EmailRepository extends JpaRepository<Email, Long>{
	Optional<Email> findByValor(String email);
}
