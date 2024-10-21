package br.edu.ifsp.spo.bike_integration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1")
@Tag(name = "Teste", description = "Teste de integração")
public class TesteController {
	
	@GetMapping("/teste")
	public String teste() {
		return "Teste de integração";
	}
}
