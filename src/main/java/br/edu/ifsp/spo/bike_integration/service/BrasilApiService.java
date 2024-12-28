package br.edu.ifsp.spo.bike_integration.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.edu.ifsp.spo.bike_integration.hardcode.BrasilApiType;
import br.edu.ifsp.spo.bike_integration.hardcode.ConfiguracaoApiType;
import br.edu.ifsp.spo.bike_integration.model.ConfiguracaoApiExterna;
import br.edu.ifsp.spo.bike_integration.response.BrasilApiCepResponse;
import br.edu.ifsp.spo.bike_integration.response.BrasilApiCnpjResponse;
import br.edu.ifsp.spo.bike_integration.util.FormatUtil;
import jakarta.annotation.PostConstruct;

@Service
public class BrasilApiService {

	@Autowired
	private ConfiguracaoApiExternaService configuracaoApiService;

	@Autowired
	private RestTemplate restTemplate;

	private ConfiguracaoApiExterna configuracao;

	@PostConstruct
	public void init() {
		configuracao = configuracaoApiService.getConfiguracaoByNome(ConfiguracaoApiType.BRASIL_API);
	}

	Logger logger = LoggerFactory.getLogger(BrasilApiService.class);

	public BrasilApiCepResponse buscarEnderecoPorCep(String cep) throws NotFoundException {
		if (!cep.matches("\\d{5}-?\\d{3}"))
			throw new IllegalArgumentException("CEP inválido.");

		try {
			return restTemplate.getForObject(
					configuracao.getUrl() + BrasilApiType.CEP.getEndpoint() + FormatUtil.removeNonNumeric(cep),
					BrasilApiCepResponse.class);
		} catch (Exception e) {
			logger.error("Erro ao buscar endereço pelo CEP: " + cep, e);
			throw new NotFoundException();
		}
	}

	public BrasilApiCnpjResponse buscarEmpresaPorCnpj(String cnpj) throws NotFoundException {
		if (!cnpj.matches("\\d{2}\\.?\\d{3}\\.?\\d{3}/?\\d{4}-?\\d{2}"))
			cnpj = FormatUtil.formatCnpj(cnpj);

		try {
			return restTemplate.getForObject(
					configuracao.getUrl() + BrasilApiType.CNPJ.getEndpoint() + FormatUtil.removeNonNumeric(cnpj),
					BrasilApiCnpjResponse.class);
		} catch (Exception e) {
			logger.error("Erro ao buscar empresa pelo CNPJ: " + cnpj, e);
			throw new NotFoundException();
		}
	}

}
