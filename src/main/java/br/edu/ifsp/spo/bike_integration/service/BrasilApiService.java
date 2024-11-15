package br.edu.ifsp.spo.bike_integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.edu.ifsp.spo.bike_integration.hardcode.ConfiguracaoApiType;
import br.edu.ifsp.spo.bike_integration.model.ConfiguracaoApiExterna;
import br.edu.ifsp.spo.bike_integration.response.BrasilApiCepResponse;

@Service
public class BrasilApiService {

	@Autowired
	private ConfiguracaoApiExternaService configuracaoApiService;

	@Autowired
	private RestTemplate restTemplate;

	public BrasilApiCepResponse buscarEnderecoPorCep(String cep) throws NotFoundException {
		if (!cep.matches("\\d{5}-?\\d{3}"))
			throw new IllegalArgumentException("CEP inválido.");

		ConfiguracaoApiExterna configuracao = configuracaoApiService
				.getConfiguracaoByNome(ConfiguracaoApiType.BRASIL_API);

		try {
			return restTemplate.getForObject(configuracao.getUrl() + ConfiguracaoApiType.BRASIL_API.getEndpoint() + cep,
					BrasilApiCepResponse.class);
		} catch (Exception e) {
			throw new NotFoundException();
		}
	}

}
