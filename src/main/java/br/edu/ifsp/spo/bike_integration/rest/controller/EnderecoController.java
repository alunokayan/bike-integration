package br.edu.ifsp.spo.bike_integration.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.response.BrasilApiCepResponse;
import br.edu.ifsp.spo.bike_integration.service.BrasilApiService;
import br.edu.ifsp.spo.bike_integration.util.FormatUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1")
@Tag(name = "Endereço", description = "Controller para operações relacionadas a endereços.")
public class EnderecoController {

	@Autowired
	private BrasilApiService brasilApiService;

	@Operation(description = "Busca endereço completo pelo cep.")
	@ApiResponse(content = @Content(schema = @Schema(implementation = BrasilApiCepResponse.class)))
	@ApiResponse(responseCode = "404", description = "Endereço não encontrado.")
	@GetMapping("/endereco/getByCep")
	public BrasilApiCepResponse buscarEnderecoPorCep(String cep) throws NotFoundException {
		return brasilApiService.buscarEnderecoPorCep(FormatUtil.formatCep(cep));
	}
}
