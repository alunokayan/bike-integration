package br.edu.ifsp.spo.bike_integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.dto.UsuarioLoginDto;
import br.edu.ifsp.spo.bike_integration.exception.CryptoException;
import br.edu.ifsp.spo.bike_integration.model.Usuario;
import br.edu.ifsp.spo.bike_integration.repository.UsuarioRepository;
import br.edu.ifsp.spo.bike_integration.util.CryptoUtil;

@Service
public class LoginService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario login(UsuarioLoginDto usuario) throws CryptoException {
		Usuario usuarioLogado = usuarioRepository.findByNomeUsuario(usuario.getNomeUsuario()).orElse(
				usuarioRepository.findByEmail(usuario.getEmail()).orElse(usuarioRepository.findByCpf(usuario.getCpf())
						.orElse(usuarioRepository.findByCnpj(usuario.getCnpj()).orElse(null))));

		if (usuarioLogado != null
				&& !CryptoUtil.encrypt(usuario.getSenha(), usuarioLogado.getHash()).equals(usuarioLogado.getSenha())) {
			throw new IllegalArgumentException("Senha inválida!");
		}

		return usuarioLogado;
	}

}
