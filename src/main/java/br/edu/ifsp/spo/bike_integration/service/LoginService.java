package br.edu.ifsp.spo.bike_integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.dto.UsuarioLoginDto;
import br.edu.ifsp.spo.bike_integration.exception.CryptoException;
import br.edu.ifsp.spo.bike_integration.model.Usuario;
import br.edu.ifsp.spo.bike_integration.repository.UsuarioRepository;
import br.edu.ifsp.spo.bike_integration.util.CryptoUtil;
import jakarta.mail.MessagingException;

@Service
public class LoginService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private EmailService emailService;

	public Usuario login(UsuarioLoginDto usuario) throws CryptoException, MessagingException {
		Usuario usuarioLogado = usuarioRepository.findByNomeUsuario(usuario.getNomeUsuario()).orElse(
				usuarioRepository.findByEmail(usuario.getEmail()).orElse(usuarioRepository.findByCpf(usuario.getCpf())
						.orElse(usuarioRepository.findByCnpj(usuario.getCnpj()).orElse(null))));

		if (usuarioLogado != null
				&& !CryptoUtil.encrypt(usuario.getSenha(), usuarioLogado.getHash()).equals(usuarioLogado.getSenha())) {
			throw new IllegalArgumentException("Senha inválida!");
		} else if (usuarioLogado == null) {
			throw new IllegalArgumentException("Usuário não encontrado!");
		}

		// Gerar token
		this.tokenService.generateToken(usuarioLogado);
		
		// Enviar email com token
		this.emailService.sendTokenEmail(usuarioLogado);
		
		return usuarioLogado;
	}

}
