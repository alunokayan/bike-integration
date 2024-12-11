package br.edu.ifsp.spo.bike_integration.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.dto.UsuarioDto;
import br.edu.ifsp.spo.bike_integration.model.Usuario;
import br.edu.ifsp.spo.bike_integration.repository.UsuarioRepository;
import br.edu.ifsp.spo.bike_integration.util.FormatUtil;
import jakarta.mail.MessagingException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private OpenStreetMapApiService openStreetMapApiService;

	@Autowired
	private NivelHabilidadeService nivelHabilidadeService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private EmailService emailService;

	public Usuario loadUsuarioById(Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	public Usuario createUsuario(UsuarioDto usuarioDto) throws MessagingException {
		// Busca as coordenadas do endereço
		Map<String, Double> coordenadas = openStreetMapApiService
				.buscarCoordenadasPorEndereco(FormatUtil.formatEnderecoToOpenStreetMapApi(usuarioDto.getEndereco()));
		usuarioDto.getEndereco().setLatitude(coordenadas.get("lat"));
		usuarioDto.getEndereco().setLongitude(coordenadas.get("lon"));

		// Salva o usuário
		Usuario usuarioSaved = usuarioRepository.save(Usuario.builder().nome(usuarioDto.getNome())
				.nomeUsuario(usuarioDto.getNomeUsuario()).endereco(usuarioDto.getEndereco())
				.email(usuarioDto.getEmail()).senha(usuarioDto.getSenha()).cpf(usuarioDto.getCpf())
				.cnpj(usuarioDto.getCnpj())
				.nivelHabilidade(nivelHabilidadeService.loadNivelHabilidade(usuarioDto.getNivelHabilidade())).build());

		// Gera o token
		tokenService.generateToken(usuarioSaved);

		// Envia o token por e-mail
		emailService.sendCadastroTokenEmail(this.loadUsuarioById(usuarioSaved.getId()));

		// Retorna o usuário
		return usuarioSaved;

	}
	
	public Usuario updateUsuario(Long id, UsuarioDto usuarioDto) {
		Usuario usuario = usuarioRepository.findById(id).orElse(null);
		if (usuario == null) {
			return null;
		}
		usuario.setNome(usuarioDto.getNome());
		usuario.setNomeUsuario(usuarioDto.getNomeUsuario());
		usuario.setEndereco(usuarioDto.getEndereco());
		usuario.setEmail(usuarioDto.getEmail());
		usuario.setSenha(usuarioDto.getSenha());
		usuario.setCpf(usuarioDto.getCpf());
		usuario.setCnpj(usuarioDto.getCnpj());
		usuario.setNivelHabilidade(nivelHabilidadeService.loadNivelHabilidade(usuarioDto.getNivelHabilidade()));
		return usuarioRepository.save(usuario);
	}
	
	public void deleteUsuario(Long id) {
		usuarioRepository.deleteById(id);
	}
}
