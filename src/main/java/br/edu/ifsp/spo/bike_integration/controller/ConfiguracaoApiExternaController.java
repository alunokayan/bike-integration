package br.edu.ifsp.spo.bike_integration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.annotation.BearerToken;
import br.edu.ifsp.spo.bike_integration.annotation.Role;
import br.edu.ifsp.spo.bike_integration.hardcode.RoleType;
import br.edu.ifsp.spo.bike_integration.model.ConfiguracaoApiExterna;
import br.edu.ifsp.spo.bike_integration.service.ConfiguracaoApiExternaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("v1/configuracao/api/externa")
@Tag(name = "Configuração API Externa", description = "Controller para operações relacionadas à configuração da API Externa.")
public class ConfiguracaoApiExternaController {

	@Autowired
	private ConfiguracaoApiExternaService configuracaoApiExternaService;

	@Role(RoleType.ADMIN)
	@BearerToken
	@GetMapping(path = "/configuracoes", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Lista todas as configurações da API externa.")
	public ResponseEntity<List<ConfiguracaoApiExterna>> listarConfiguracoes() {
		return ResponseEntity.ok(configuracaoApiExternaService.listarConfiguracoes());
	}

	@Role(RoleType.ADMIN)
	@BearerToken
	@GetMapping(path = "/configuracoes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Retorna uma configuração da API externa por ID.")
	public ResponseEntity<ConfiguracaoApiExterna> obterConfiguracaoPorId(@PathVariable("id") Long id) {
		return ResponseEntity.ok(configuracaoApiExternaService.getConfiguracaoById(id));
	}

	@Role(RoleType.ADMIN)
	@BearerToken
	@GetMapping(path = "/configuracoes/nome/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Retorna configurações da API externa filtradas pelo nome.")
	public ResponseEntity<List<ConfiguracaoApiExterna>> obterConfiguracoesPorNome(
			@PathVariable("nome") String nome) {
		return ResponseEntity.ok(configuracaoApiExternaService.getConfiguracaoByNome(nome));
	}
}