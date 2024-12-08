package br.edu.ifsp.spo.bike_integration.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.edu.ifsp.spo.bike_integration.hardcode.ConfiguracaoApiType;
import br.edu.ifsp.spo.bike_integration.model.ConfiguracaoApiExterna;
import jakarta.annotation.PostConstruct;

@Service
public class OpenStreetMapApiService {

	@Autowired
	private ConfiguracaoApiExternaService configuracaoApiService;

	@Autowired
	private RestTemplate restTemplate;

	private ConfiguracaoApiExterna configuracao;

	@PostConstruct
	public void init() {
		configuracao = configuracaoApiService.getConfiguracaoByNome(ConfiguracaoApiType.OPEN_STREET_MAP_API);
	}

	Logger logger = LoggerFactory.getLogger(OpenStreetMapApiService.class);

	public Map<String, Double> buscarCoordenadasPorEndereco(String endereco) {
		if (endereco == null || endereco.isEmpty())
			throw new IllegalArgumentException("Endereço inválido.");

		try {
			ResponseEntity<Map<String, Object>[]> responseEntity = restTemplate.exchange(
					configuracao.getUrl() + "?q=" + endereco + "&format=jsonv2", HttpMethod.GET, null,
					new ParameterizedTypeReference<Map<String, Object>[]>() {
					});
			Map<String, Object>[] response = responseEntity.getBody();
			if (response != null && response.length > 0) {
				Map<String, Object> firstResult = response[0];
				Double lat = Double.valueOf((String) firstResult.get("lat"));
				Double lon = Double.valueOf((String) firstResult.get("lon"));
				return Map.of("lat", lat, "lon", lon);
			}
			throw new IllegalArgumentException("Endereço não encontrado");
		} catch (Exception e) {
			logger.error("Erro ao buscar coordenadas do endereço: " + endereco, e);
			throw new IllegalArgumentException("Endereço não encontrado");
		}
	}
}
