package br.edu.ifsp.spo.bike_integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.annotation.BearerAuthentication;
import br.edu.ifsp.spo.bike_integration.annotation.JwtSecretKey;
import br.edu.ifsp.spo.bike_integration.annotation.Role;
import br.edu.ifsp.spo.bike_integration.dto.UsuarioLoginDTO;
import br.edu.ifsp.spo.bike_integration.exception.CryptoException;
import br.edu.ifsp.spo.bike_integration.hardcode.RoleType;
import br.edu.ifsp.spo.bike_integration.model.Usuario;
import br.edu.ifsp.spo.bike_integration.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("v1/login")
@Tag(name = "Login", description = "Controller para operações relacionadas a login.")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Role(RoleType.ADMIN)
	@JwtSecretKey
	@PostMapping(path = "/do", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Realiza o login.")
	public ResponseEntity<Usuario> login(@RequestBody UsuarioLoginDTO usuario)
			throws CryptoException, MessagingException {
		return ResponseEntity.ok(loginService.login(usuario));
	}

	@Role({ RoleType.PF, RoleType.PJ })
	@BearerAuthentication
	@PostMapping(path = "/recover")
	@Operation(summary = "Recupera a senha.")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void recoverPassword(@RequestParam String idUsuario, @RequestParam String token, String novaSenha)
			throws MessagingException, CryptoException {
		loginService.recoverPassword(idUsuario, token, novaSenha);
	}
}
