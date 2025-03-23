package br.edu.ifsp.spo.bike_integration.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifsp.spo.bike_integration.dto.UsuarioAdmDTO;
import br.edu.ifsp.spo.bike_integration.dto.UsuarioDTO;
import br.edu.ifsp.spo.bike_integration.exception.BikeIntegrationCustomException;
import br.edu.ifsp.spo.bike_integration.hardcode.RoleType;
import br.edu.ifsp.spo.bike_integration.model.Usuario;
import br.edu.ifsp.spo.bike_integration.repository.UsuarioRepository;
import br.edu.ifsp.spo.bike_integration.rest.service.OpenStreetMapApiService;
import br.edu.ifsp.spo.bike_integration.util.FormatUtil;
import br.edu.ifsp.spo.bike_integration.util.validate.CpfValidate;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private OpenStreetMapApiService openStreetMapApiService;

	@Autowired
	private NivelHabilidadeService nivelHabilidadeService;

	@Autowired
	private SessaoService sessaoService;

	public Usuario loadUsuarioById(Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	public Usuario loadUsuarioByEmail(String email) {
		return usuarioRepository.findByEmail(email).orElse(null);
	}

	public Usuario loadUsuarioByNomeUsuario(String nomeUsuario) {
		return usuarioRepository.findByNomeUsuario(nomeUsuario).orElse(null);
	}

	public Usuario createUsuario(UsuarioDTO usuarioDto) {
		// Busca as coordenadas do endereço
		Map<String, Double> coordenadas = openStreetMapApiService
				.buscarCoordenadasPorEndereco(FormatUtil.formatEnderecoToOpenStreetMapApi(usuarioDto.getEndereco()));
		usuarioDto.getEndereco().setLatitude(coordenadas.get("lat"));
		usuarioDto.getEndereco().setLongitude(coordenadas.get("lon"));

		// Salva o usuário
		Usuario usuario = usuarioRepository.save(Usuario.builder().nome(usuarioDto.getNome())
				.nomeUsuario(usuarioDto.getNomeUsuario()).endereco(usuarioDto.getEndereco())
				.email(usuarioDto.getEmail()).senha(usuarioDto.getSenha()).cpf(usuarioDto.getCpf())
				.cnpj(usuarioDto.getCnpj())
				.nivelHabilidade(nivelHabilidadeService.loadNivelHabilidade(usuarioDto.getNivelHabilidade()))
				.role(usuarioDto.getCpf() != null ? RoleType.PF
						: usuarioDto.getCnpj() != null ? RoleType.PJ : RoleType.ADMIN)
				.build());

		// Salva sessão
		sessaoService.create(usuario);

		return this.loadUsuarioById(usuario.getId());

	}

	public Usuario createUsuarioAdm(UsuarioAdmDTO usuarioAdmoDto) {
		// Salva o usuário
		Usuario usuario = usuarioRepository.save(Usuario.builder().nome(usuarioAdmoDto.getNome())
				.nomeUsuario(usuarioAdmoDto.getNomeUsuario()).email(usuarioAdmoDto.getEmail())
				.senha(usuarioAdmoDto.getSenha())
				.role(RoleType.ADMIN).build());

		// Salva sessão
		sessaoService.create(usuario);

		return this.loadUsuarioById(usuario.getId());
	}

	@Modifying
	@Transactional
	public Usuario updateUsuario(Long id, UsuarioDTO usuarioDto) {
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

	public void updateFotoUsuario(Long id, MultipartFile file) {
		try {
			usuarioRepository.saveFoto(id, file.getBytes());
		} catch (IOException e) {
			throw new BikeIntegrationCustomException("Erro ao salvar foto do usuário", e);
		}
	}

	public void deleteUsuario(Long id) {
		usuarioRepository.deleteById(id);
	}

	public String validateCpf(String cpf) {
		return CpfValidate.validate(cpf);
	}
}
