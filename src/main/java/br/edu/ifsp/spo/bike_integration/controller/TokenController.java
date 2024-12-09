package br.edu.ifsp.spo.bike_integration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.model.Token;
import br.edu.ifsp.spo.bike_integration.service.EmailService;
import br.edu.ifsp.spo.bike_integration.service.TokenService;
import br.edu.ifsp.spo.bike_integration.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/v1/token")
@Tag(name = "Token", description = "Controller para operações relacionadas a token.")
public class TokenController {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/isValidToken")
	@Operation(summary = "Valida o token.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Token válido."),
			@ApiResponse(responseCode = "500", description = "Token inválido.") })
	public boolean isValidateToken(@RequestParam String token, @RequestParam Long idUsuario) {
		return tokenService.isValidToken(token, idUsuario);
	}

	@PostMapping("/resendNewToken")
	@Operation(summary = "Reenvia um novo token.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Token reenviado com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao reenviar o token.") })
	public ResponseEntity<Void> sendTokenEmail(@RequestParam Long idUsuario) throws MessagingException {
		tokenService.generateToken(this.usuarioService.loadUsuarioById(idUsuario));
		emailService.sendLoginTokenEmail(this.usuarioService.loadUsuarioById(idUsuario));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/listByUser")
	@Operation(summary = "Lista os tokens de um usuário.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Tokens listados com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao listar os tokens.") })
	public List<Token> listTokensByUser(@RequestParam Long idUsuario) {
		return tokenService.listTokensByUser(idUsuario);
	}
}
