package br.edu.ifsp.spo.bike_integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.dto.UsuarioDto;
import br.edu.ifsp.spo.bike_integration.service.UsuarioService;
import br.edu.ifsp.spo.bike_integration.util.FormatUtil;
import br.edu.ifsp.spo.bike_integration.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1")
@Tag(name = "Usuario", description = "Controller para operações relacionadas a usuários.")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Operation(description = "Cria um novo usuário.")
	@PostMapping("/usuario/create")
	public ResponseEntity<Object> create(@RequestBody UsuarioDto usuario) throws Exception {
		return ResponseUtil.createResponse("Usuário criado com sucesso id: " + usuarioService.create(usuario).getId(),
				HttpStatus.CREATED);
	}

	@Operation(description = "Deleta um usuário.")
	@DeleteMapping("/usuario/delete")
	public ResponseEntity<Object> delete(@RequestParam String nomeUsuario, @RequestParam(required = false) String cpf,
			@RequestParam(required = false) String cnpj) {
		usuarioService.delete(nomeUsuario, FormatUtil.formatCpf(cpf), FormatUtil.formatCnpj(cnpj));
		return ResponseUtil.createResponse("Usuário deletado com sucesso", HttpStatus.OK);
	}
}
