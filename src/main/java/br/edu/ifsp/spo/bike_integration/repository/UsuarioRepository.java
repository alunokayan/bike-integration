package br.edu.ifsp.spo.bike_integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifsp.spo.bike_integration.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findById(Long id);

	Optional<Usuario> findByNomeUsuario(String nomeUsuario);

	Optional<Usuario> findByEmail(String email);

	Optional<Usuario> findByCpf(String cpf);

	Optional<Usuario> findByCnpj(String cnpj);

	@Query(value = "SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Usuario u WHERE u.nomeUsuario = :nomeUsuario OR u.email = :email")
	Boolean existsByNomeUsuarioOrEmail(@Param("nomeUsuario") String nomeUsuario, @Param("email") String email);
}