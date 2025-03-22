package br.edu.ifsp.spo.bike_integration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@Tag(name = "Configuração API Externa", description = "Controller para operações relacionadas a configuração da API Externa.")
public class ConfiguracaoApiExternaController {

	@Autowired
	private ConfiguracaoApiExternaService configuracaoApiExternaService;

	@Role(RoleType.ADMIN)
	@BearerToken
	@GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Lista as API's Externa.")
	public ResponseEntity<List<ConfiguracaoApiExterna>> listarConfiguracaoApiExterna() {
		return ResponseEntity.ok(configuracaoApiExternaService.listarConfiguracoes());
	}

	@Role(RoleType.ADMIN)
	@BearerToken
	@GetMapping(path = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Retorna a configuração da API Externa.")
	public ResponseEntity<ConfiguracaoApiExterna> getConfiguracaoApiExterna(@RequestParam(required = true) Long id) {
		return ResponseEntity.ok(configuracaoApiExternaService.getConfiguracaoById(id));
	}

	@Role(RoleType.ADMIN)
	@BearerToken
	@GetMapping(path = "/get/all/by/nome", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Retorna a configuração da API Externa.")
	public ResponseEntity<List<ConfiguracaoApiExterna>> getConfiguracaoApiExterna(
			@RequestParam(required = true) String nome) {
		return ResponseEntity.ok(configuracaoApiExternaService.getConfiguracaoByNome(nome));
	}

}
