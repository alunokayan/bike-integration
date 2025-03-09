package br.edu.ifsp.spo.bike_integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifsp.spo.bike_integration.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findById(Long id);

	Optional<Usuario> findByNomeUsuario(String nomeUsuario);

	Optional<Usuario> findByEmail(String email);

	Optional<Usuario> findByCpf(String cpf);

	Optional<Usuario> findByCnpj(String cnpj);

	@Modifying
	@Transactional
	@Query("UPDATE Usuario e SET e.foto = :foto WHERE e.id = :id")
	void saveFoto(@Param("id") Long id, @Param("foto") byte[] foto);
}