package br.edu.ifsp.spo.bike_integration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.hardcode.ConfiguracaoApiType;
import br.edu.ifsp.spo.bike_integration.model.ConfiguracaoApiExterna;
import br.edu.ifsp.spo.bike_integration.repository.ConfiguracaoApiExternaRepository;

@Service
public class ConfiguracaoApiExternaService {

	@Autowired
	private ConfiguracaoApiExternaRepository configuracaoApiRepository;

	public List<ConfiguracaoApiExterna> getConfiguracaoByNome(String nome) {
		return configuracaoApiRepository.findAllByNome(nome);
	}

	public ConfiguracaoApiExterna getConfiguracaoByType(ConfiguracaoApiType apiType) {
		return configuracaoApiRepository.findByNome(apiType.getNome()).orElse(null);
	}

	public ConfiguracaoApiExterna getConfiguracaoById(Long id) {
		return configuracaoApiRepository.findById(id).orElse(null);
	}

	public List<ConfiguracaoApiExterna> listarConfiguracoes() {
		return configuracaoApiRepository.findAll();
	}

}
