package br.edu.ifsp.spo.bike_integration.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.dto.UsuarioDto;
import br.edu.ifsp.spo.bike_integration.model.Usuario;
import br.edu.ifsp.spo.bike_integration.repository.UsuarioRepository;
import br.edu.ifsp.spo.bike_integration.util.FormatUtil;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private OpenStreetMapApiService openStreetMapApiService;

	@Autowired
	private NivelHabilidadeService nivelHabilidadeService;

	public Usuario loadUsuarioById(Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	public Usuario createUsuario(UsuarioDto usuarioDto) {
		Map<String, Double> coordenadas = openStreetMapApiService
				.buscarCoordenadasPorEndereco(FormatUtil.formatEnderecoToOpenStreetMapApi(usuarioDto.getEndereco()));
		usuarioDto.getEndereco().setLatitude(coordenadas.get("lat"));
		usuarioDto.getEndereco().setLongitude(coordenadas.get("lon"));

		return usuarioRepository.save(Usuario.builder().nome(usuarioDto.getNome())
				.nomeUsuario(usuarioDto.getNomeUsuario()).endereco(usuarioDto.getEndereco())
				.email(usuarioDto.getEmail()).senha(usuarioDto.getSenha()).cpf(usuarioDto.getCpf())
				.cnpj(usuarioDto.getCnpj())
				.nivelHabilidade(nivelHabilidadeService.loadNivelHabilidade(usuarioDto.getNivelHabilidade())).build());
	}
}
