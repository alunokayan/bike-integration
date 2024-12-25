package br.edu.ifsp.spo.bike_integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/app")
@Tag(name = "Index", description = "Index Controller, com informações da API")
public class IndexController {

	@Autowired
	BuildProperties buildProperties;

	@Operation(summary = "versão da API", description = "Retorna a versão da API")
	@GetMapping({ "/", "home", "ping", "version" })
	public String version() {
		return buildProperties.getVersion();
	}

	@Operation(summary = "detalhes da API", description = "Retorna os detalhes da API")
	@GetMapping("detail")
	public String details() {
		return "Name: %s%nGroup: %s%nVersion: %s%nTime: %s%nArtifact: %s".formatted(
				buildProperties.getName(),
				buildProperties.getGroup(),
				buildProperties.getVersion(),
				buildProperties.getTime(),
				buildProperties.getArtifact());
	}
}
