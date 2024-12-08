package br.edu.ifsp.spo.bike_integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.dto.UsuarioLoginDto;
import br.edu.ifsp.spo.bike_integration.exception.CryptoException;
import br.edu.ifsp.spo.bike_integration.model.Usuario;
import br.edu.ifsp.spo.bike_integration.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/v1/login")
@Tag(name = "Login", description = "Controller para operações relacionadas a login.")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@GetMapping("/do")
	@Operation(summary = "Realiza o login.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Login realizado com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao realizar login.") })
	public Usuario login(@RequestBody UsuarioLoginDto usuario) throws CryptoException, MessagingException {
		return loginService.login(usuario);
	}
}
