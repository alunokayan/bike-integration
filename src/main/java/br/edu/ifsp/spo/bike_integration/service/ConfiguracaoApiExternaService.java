package br.edu.ifsp.spo.bike_integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.hardcode.ConfiguracaoApiType;
import br.edu.ifsp.spo.bike_integration.model.ConfiguracaoApiExterna;
import br.edu.ifsp.spo.bike_integration.repository.ConfiguracaoApiExternaRepository;

@Service
public class ConfiguracaoApiExternaService {

	@Autowired
	private ConfiguracaoApiExternaRepository configuracaoApiRepository;
	
	public ConfiguracaoApiExterna getConfiguracaoByNome(ConfiguracaoApiType apiType) {
		return this.getConfiguracaoByNome(apiType.getNome());
	}
	
	private ConfiguracaoApiExterna getConfiguracaoByNome(String nome) {
		return configuracaoApiRepository.findByNome(nome)
				.orElseThrow(() -> new RuntimeException("API não encontrada"));
	}
	
}
