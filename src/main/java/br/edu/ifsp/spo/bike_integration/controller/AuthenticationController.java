package br.edu.ifsp.spo.bike_integration.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.dto.JwtSubjectDTO;
import br.edu.ifsp.spo.bike_integration.exception.BikeException;
import br.edu.ifsp.spo.bike_integration.jwt.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("v1/auth")
@Tag(name = "Autenticação", description = "Controller para operações relacionadas a autenticação")
public class AuthenticationController {

	private final JwtService service;

	public AuthenticationController(final JwtService service) {
		this.service = service;
	}

	@Operation(summary = "Efetua a autenticação", description = "Efetua a autenticação no serviço, devolvendo o token JWT")
	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> authenticate(
			@NotBlank(message = "Access Key é obrigatório") @RequestParam String accessKey,
			@NotBlank(message = "Secret Key é obrigatório") @RequestParam String secretKey) throws BikeException {
		return Map.of("token",
				this.service.create(JwtSubjectDTO.builder().accessKey(accessKey).secretKey(secretKey).build()));
	}

	@GetMapping
	@Operation(summary = "Endpoint simples para verificar se está autenticado", description = "Endpoint simples para verificar se está autenticado")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void isAuthenticated() {
		// NO-OP
	}

}