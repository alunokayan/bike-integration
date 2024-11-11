package br.edu.ifsp.spo.bike_integration.service;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.dto.UsuarioDto;
import br.edu.ifsp.spo.bike_integration.exception.CryptoException;
import br.edu.ifsp.spo.bike_integration.model.Senha;
import br.edu.ifsp.spo.bike_integration.model.TipoUsuario;
import br.edu.ifsp.spo.bike_integration.model.Usuario;
import br.edu.ifsp.spo.bike_integration.repository.UsuarioRepository;
import br.edu.ifsp.spo.bike_integration.util.CryptoUtil;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	private UsuarioService() {
	}

	public Usuario create(UsuarioDto usuario) throws CryptoException {
		if (usuarioRepository.findByNomeUsuario(usuario.getNome()).isPresent()) {
			throw new IllegalArgumentException("Nome de usuário já existe");
		}
		// Gera chave de criptografia
		SecretKey key = CryptoUtil.generateKey();

		// Criptografa a senha
		String senhaCriptografada = CryptoUtil.encrypt(usuario.getSenha(), key);

		// Salva usuário
		return usuarioRepository.save(
				Usuario.builder()
				.nomeUsuario(usuario.getNome())
				.senha(Senha.builder()
						.valor(senhaCriptografada)
						.chave(CryptoUtil.getSecretKeyAsString(key))
						.build())
				.tipoUsuario(TipoUsuario.builder()
						.id(usuario.getIdTipoUsuario())
                        .build())
				.build());
	}
}