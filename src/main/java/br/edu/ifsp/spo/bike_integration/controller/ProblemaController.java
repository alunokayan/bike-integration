package br.edu.ifsp.spo.bike_integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.dto.ProblemaDto;
import br.edu.ifsp.spo.bike_integration.service.ProblemaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/v1/problema")
public class ProblemaController {

	@Autowired
	private ProblemaService problemaService;

	@PostMapping("/do")
	@Operation(summary = "Registra um problema.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Problema registrado com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao registrar problema.") })
	public ResponseEntity<Void> registrar(@RequestBody ProblemaDto problema) {
		problemaService.registrar(problema);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
