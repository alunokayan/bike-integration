package br.edu.ifsp.spo.bike_integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.spo.bike_integration.model.Sessao;
import br.edu.ifsp.spo.bike_integration.model.Usuario;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    Optional<Sessao> findByToken(String token);

    Optional<Sessao> findByUsuario(Usuario usuario);

}
