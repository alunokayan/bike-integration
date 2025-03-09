package br.edu.ifsp.spo.bike_integration.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("v1/app")
@Tag(name = "Index", description = "Controller para operações básicas.")
public class IndexController {

	@Autowired
	BuildProperties buildProperties;

	@GetMapping(path = { "/", "home", "ping", "version" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "versão da API", description = "Retorna a versão da API")
	public Map<String, String> version() {
		return Map.of("version", buildProperties.getVersion());
	}

	@GetMapping(path = "detail", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "detalhes da API", description = "Retorna os detalhes da API")
	public Map<String, Object> details() {
		return Map.of("name", buildProperties.getName(), "group", buildProperties.getGroup(), "version",
				buildProperties.getVersion(), "time", buildProperties.getTime(), "artifact",
				buildProperties.getArtifact());
	}
}
