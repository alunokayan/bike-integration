package br.edu.ifsp.spo.bike_integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.annotation.BearerToken;
import br.edu.ifsp.spo.bike_integration.annotation.Role;
import br.edu.ifsp.spo.bike_integration.dto.ProblemaDTO;
import br.edu.ifsp.spo.bike_integration.hardcode.RoleType;
import br.edu.ifsp.spo.bike_integration.service.ProblemaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("v1/problema")
@Tag(name = "Problema", description = "Controller para operações relacionadas a problemas.")
public class ProblemaController {

	@Autowired
	private ProblemaService problemaService;

	@Role(RoleType.PF)
	@BearerToken
	@PostMapping(path = "/do", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Registra um problema.")
	@ResponseStatus(HttpStatus.CREATED)
	public void registrar(@RequestBody ProblemaDTO problema) {
		problemaService.registrar(problema);
	}

}
