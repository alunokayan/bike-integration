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

		if (!usuario.getSenha().isBlank() && usuarioLogado != null && !usuario.getSenha().equals(CryptoUtil
				.decrypt(usuarioLogado.getSenha(), CryptoUtil.getSecretKeyFromString(usuarioLogado.getHash())))) {
			throw new IllegalArgumentException("Usuário ou senha inválidos!");
		}

		// Gerar token
		this.tokenService.generateToken(usuarioLogado);

		// Enviar email com token
		this.emailService.sendLoginTokenEmail(usuarioLogado);

		return usuarioLogado;
	}

	public void recoverPassword(String idUsuario, String token, String novaSenha)
			throws CryptoException, MessagingException {
		Usuario usuario = this.usuarioRepository.findById(Long.parseLong(idUsuario)).orElse(null);

		if (usuario == null) {
			throw new IllegalArgumentException("Usuário não encontrado!");
		}

		if (Boolean.FALSE.equals(this.tokenService.isValidToken(token, usuario.getId()))) {
			throw new IllegalArgumentException("Token inválido!");
		}

		usuario.setHash(CryptoUtil.generateKeyAsString());
		usuario.setSenha(CryptoUtil.encrypt(novaSenha, usuario.getHash()));
		this.usuarioRepository.save(usuario);

		this.tokenService.disableToken(this.tokenService.getToken(token));

		this.emailService.sendRecuperacaoTokenEmail(usuario);
	}

}
