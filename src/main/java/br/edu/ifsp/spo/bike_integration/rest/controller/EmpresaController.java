package br.edu.ifsp.spo.bike_integration.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.response.BrasilApiCnpjResponse;
import br.edu.ifsp.spo.bike_integration.rest.service.BrasilApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("v1/empresa")
@Tag(name = "Empresa", description = "Controller para operações relacioadas a empresa.")
public class EmpresaController {

	@Autowired
	private BrasilApiService brasilApiService;

	@GetMapping(path = "/getByCnpj", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Busca informacoes da empresa pelo cnpj.")
	public ResponseEntity<BrasilApiCnpjResponse> buscarEmpresaPorCnpj(String cnpj) throws NotFoundException {
		return ResponseEntity.ok(brasilApiService.buscarEmpresaPorCnpj(cnpj));
	}

	@GetMapping(path = "/isActiveCnpj", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Valida se o cnpj é ativo.")
	public ResponseEntity<Boolean> validateCnpj(String cnpj) throws NotFoundException {
		return brasilApiService.buscarEmpresaPorCnpj(cnpj).getDescricaoSituacaoCadastral().equals("ATIVA")
				? ResponseEntity.ok(true)
				: ResponseEntity.ok(false);
	}
}
