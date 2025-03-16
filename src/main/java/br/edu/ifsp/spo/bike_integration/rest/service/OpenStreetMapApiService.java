package br.edu.ifsp.spo.bike_integration.rest.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.edu.ifsp.spo.bike_integration.exception.BikeIntegrationCustomException;
import br.edu.ifsp.spo.bike_integration.hardcode.ConfiguracaoApiType;
import br.edu.ifsp.spo.bike_integration.hardcode.OpenStreetMapApiType;
import br.edu.ifsp.spo.bike_integration.model.ConfiguracaoApiExterna;
import br.edu.ifsp.spo.bike_integration.service.ConfiguracaoApiExternaService;
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
		configuracao = configuracaoApiService.getConfiguracaoByType(ConfiguracaoApiType.OPEN_STREET_MAP_API);
	}

	Logger logger = LoggerFactory.getLogger(OpenStreetMapApiService.class);

	public Map<String, Double> buscarCoordenadasPorEndereco(String endereco) {
		if (endereco == null || endereco.isEmpty())
			throw new IllegalArgumentException("Endereço inválido.");

		try {
			ResponseEntity<Map<String, Object>[]> responseEntity = restTemplate.exchange(
					configuracao.getUrl() + OpenStreetMapApiType.SEARCH.getEndpoint() + "?q=" + endereco
							+ "&format=jsonv2",
					HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>[]>() {
					});
			Map<String, Object>[] response = responseEntity.getBody();
			if (response != null && response.length > 0) {
				Map<String, Object> firstResult = response[0];
				Double lat = Double.valueOf((String) firstResult.get("lat"));
				Double lon = Double.valueOf((String) firstResult.get("lon"));
				return Map.of("lat", lat, "lon", lon);
			}
			throw new IllegalArgumentException();
		} catch (Exception e) {
			logger.error("Erro ao buscar coordenadas do endereço: " + endereco, e);
			throw new BikeIntegrationCustomException("Erro ao buscar coordenadas do endereço: " + endereco, e);
		}
	}

	public Map<String, String> buscarCepPorCoordenadas(String latitude, String longitude) {
		if (latitude == null || latitude.isEmpty() || longitude == null || longitude.isEmpty())
			throw new IllegalArgumentException("Coordenadas inválidas.");

		try {
			ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
					configuracao.getUrl() + OpenStreetMapApiType.REVERSE.getEndpoint() + "?lat=" + latitude + "&lon="
							+ longitude + "&format=jsonv2",
					HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {
					});
			Map<String, Object> response = responseEntity.getBody();
			if (response != null) {
				@SuppressWarnings("unchecked")
				Map<String, Object> address = (Map<String, Object>) response.get("address");
				if (address != null) {
					String cep = (String) address.get("postcode");
					return Map.of("cep", cep.replace("-", ""));
				}
			}
			throw new IllegalArgumentException();
		} catch (Exception e) {
			logger.error("Erro ao buscar cep das coordenadas: " + latitude + ", " + longitude, e);
			throw new BikeIntegrationCustomException(
					"Erro ao buscar cep das coordenadas: " + latitude + ", " + longitude, e);
		}
	}

	public Map<String, String> buscaCoordenadasPorCep(String cep) {
		if (cep == null || cep.isEmpty())
			throw new IllegalArgumentException("CEP inválido.");

		try {
			ResponseEntity<Map<String, Object>[]> responseEntity = restTemplate.exchange(
					configuracao.getUrl() + OpenStreetMapApiType.SEARCH.getEndpoint() + "?q=" + cep + "&format=jsonv2",
					HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>[]>() {
					});
			Map<String, Object>[] response = responseEntity.getBody();
			if (response != null && response.length > 0) {
				Map<String, Object> firstResult = response[0];
				Double lat = Double.valueOf((String) firstResult.get("lat"));
				Double lon = Double.valueOf((String) firstResult.get("lon"));
				return Map.of("lat", lat.toString(), "lon", lon.toString());
			}
			throw new IllegalArgumentException();
		} catch (Exception e) {
			logger.error("Erro ao buscar coordenadas do CEP: " + cep, e);
			throw new BikeIntegrationCustomException("Erro ao buscar coordenadas do CEP: " + cep, e);
		}
	}

}
