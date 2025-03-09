package br.edu.ifsp.spo.bike_integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.dto.UsuarioDto;
import br.edu.ifsp.spo.bike_integration.model.Usuario;
import br.edu.ifsp.spo.bike_integration.service.UsuarioService;
import br.edu.ifsp.spo.bike_integration.util.validate.CpfValidate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("v1/usuario")
@Tag(name = "Usuario", description = "Controller para operações relacionadas a usuários.")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping(path = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Retorna um usuário pelo seu ID.")
	public ResponseEntity<Usuario> get(@RequestParam Long id) {
		return ResponseEntity.ok(usuarioService.loadUsuarioById(id));
	}

	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Cria um novo usuário.")
	public ResponseEntity<Usuario> create(@RequestBody UsuarioDto usuarioDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.createUsuario(usuarioDto));
	}

	@PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Atualiza um usuário.")
	public ResponseEntity<Usuario> update(@RequestParam Long id, @RequestBody UsuarioDto usuarioDto) {
		return ResponseEntity.ok(usuarioService.updateUsuario(id, usuarioDto));
	}

	@DeleteMapping(path = "/delete")
	@Operation(summary = "Deleta um usuário.")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@RequestParam Long id) {
		usuarioService.deleteUsuario(id);
	}

	@GetMapping(path = "/validate/cpf")
	@Operation(summary = "Valida um CPF.")
	public ResponseEntity<String> validateCpf(@RequestParam String cpf) {
		return ResponseEntity.ok(CpfValidate.validate(cpf));
	}
}
