package br.edu.ifsp.spo.bike_integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.dto.UsuarioDto;
import br.edu.ifsp.spo.bike_integration.exception.CryptoException;
import br.edu.ifsp.spo.bike_integration.model.Usuario;
import br.edu.ifsp.spo.bike_integration.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1")
@Tag(name = "Usuario", description = "Controller para operações relacionadas a usuários.")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Operation(description = "Cria um novo usuário.")
	@PostMapping("/create")
	public Usuario create(@RequestBody UsuarioDto usuario) throws CryptoException {
		return usuarioService.create(usuario);
	}
}
