package br.edu.ifsp.spo.bike_integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.spo.bike_integration.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findById(Long id);

	Optional<Usuario> findByNomeUsuario(String nomeUsuario);

	Optional<Usuario> findByEmail(String email);

	Optional<Usuario> findByCpf(String cpf);

	Optional<Usuario> findByCnpj(String cnpj);
}