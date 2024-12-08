package br.edu.ifsp.spo.bike_integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.dto.UsuarioDto;
import br.edu.ifsp.spo.bike_integration.model.Usuario;
import br.edu.ifsp.spo.bike_integration.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/v1/usuario")
@Tag(name = "Usuario", description = "Controller para operações relacionadas a usuários.")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/get")
	@Operation(summary = "Retorna um usuário pelo seu ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Erro na requisição.") })
	public Usuario get(@RequestParam Long id) {
		return usuarioService.loadUsuarioById(id);
	}
	
	@PostMapping("/create")
	@Operation(summary = "Cria um novo usuário.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Erro na requisição.") })
	public Usuario create(@RequestBody UsuarioDto usuarioDto) throws MessagingException {
		return usuarioService.createUsuario(usuarioDto);
	}
}
