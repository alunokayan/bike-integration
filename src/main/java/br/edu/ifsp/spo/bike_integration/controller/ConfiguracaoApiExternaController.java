package br.edu.ifsp.spo.bike_integration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.model.ConfiguracaoApiExterna;
import br.edu.ifsp.spo.bike_integration.service.ConfiguracaoApiExternaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/configuracao-api-externa")
@Tag(name = "Configuração API Externa", description = "Controller para operações relacionadas a configuração da API Externa.")
public class ConfiguracaoApiExternaController {

	@Autowired
	private ConfiguracaoApiExternaService configuracaoApiExternaService;

	@GetMapping("/list")
	@Operation(summary = "Lista as API's Externa.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "API's listadas com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao listar API's.") })
	public ResponseEntity<List<ConfiguracaoApiExterna>> listarConfiguracaoApiExterna() {
		return ResponseEntity.ok(configuracaoApiExternaService.listarConfiguracoes());
	}

	@GetMapping("/get")
	@Operation(summary = "Retorna a configuração da API Externa.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "API listada com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao listar API.") })
	public ResponseEntity<ConfiguracaoApiExterna> getConfiguracaoApiExterna(@RequestParam(required = true) Long id) {
		return ResponseEntity.ok(configuracaoApiExternaService.getConfiguracaoById(id));
	}

	@GetMapping("/getByNome")
	@Operation(summary = "Retorna a configuração da API Externa.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "API listada com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao listar API.") })
	public ResponseEntity<List<ConfiguracaoApiExterna>> getConfiguracaoApiExterna(
			@RequestParam(required = true) String nome) {
		return ResponseEntity.ok(configuracaoApiExternaService.getConfiguracaoByNome(nome));
	}

	/*
	 * TODO: Implementar os métodos abaixo.
	 */
//	@PutMapping("/update")
//	@Operation(summary = "Atualiza a configuração da API Externa.")
//	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "API atualizada com sucesso."),
//			@ApiResponse(responseCode = "500", description = "Erro ao atualizar API.") })
//	public ResponseEntity<ConfiguracaoApiExterna> atualizarConfiguracaoApiExterna(
//			@RequestParam(required = true) Long id, @RequestBody ConfiguracaoApiExterna configuracaoApiExterna) {
//		return ResponseEntity.ok(configuracaoApiExternaService.updateConfiguracao(id, configuracaoApiExterna));
//	}
//
//	@PostMapping("/create")
//	@Operation(summary = "Cadastra uma nova configuração da API Externa.")
//	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "API cadastrada com sucesso."),
//			@ApiResponse(responseCode = "500", description = "Erro ao cadastrar API.") })
//	public ResponseEntity<ConfiguracaoApiExterna> cadastrarConfiguracaoApiExterna(
//			@RequestBody ConfiguracaoApiExterna configuracaoApiExterna) {
//		return ResponseEntity.status(201)
//				.body(configuracaoApiExternaService.createConfiguracao(configuracaoApiExterna));
//	}

}
