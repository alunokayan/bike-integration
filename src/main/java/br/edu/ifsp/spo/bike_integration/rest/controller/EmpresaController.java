package br.edu.ifsp.spo.bike_integration.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.response.BrasilApiCnpjResponse;
import br.edu.ifsp.spo.bike_integration.service.BrasilApiService;
import br.edu.ifsp.spo.bike_integration.util.FormatUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1")
@Tag(name = "Empresa", description = "Controller para operações relacioadas a empresa.")
public class EmpresaController {

	@Autowired
	private BrasilApiService brasilApiService;

	@Operation(description = "Busca informacoes da empresa pelo cnpj.")
	@ApiResponse(content = @Content(schema = @Schema(implementation = BrasilApiCnpjResponse.class)))
	@GetMapping("/empresa/getByCnpj")
	public BrasilApiCnpjResponse buscarEmpresaPorCnpj(String cnpj) throws NotFoundException {
		return brasilApiService.buscarEmpresaPorCnpj(FormatUtil.formatCnpj(cnpj));
	}
}
