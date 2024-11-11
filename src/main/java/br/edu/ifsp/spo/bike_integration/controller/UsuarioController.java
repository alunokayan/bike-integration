package br.edu.ifsp.spo.bike_integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.dto.UsuarioDto;
import br.edu.ifsp.spo.bike_integration.exception.CryptoException;
import br.edu.ifsp.spo.bike_integration.service.UsuarioService;
import br.edu.ifsp.spo.bike_integration.util.FormatUtil;
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
	public String create(@RequestBody UsuarioDto usuario) throws CryptoException {
		try {
			usuarioService.create(usuario);
			return "Usuário criado com sucesso";
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Erro ao criar usuário: " + e.getMessage());
		}
	}

	@Operation(description = "Deleta um usuário.")
	@DeleteMapping("/delete")
	public String delete(@RequestParam String nomeUsuario, @RequestParam(required = false) String cpf,
			@RequestParam(required = false) String cnpj) {
		try {
			cpf = FormatUtil.formatCpf(cpf);
	        cnpj = FormatUtil.formatCnpj(cnpj);
			usuarioService.delete(nomeUsuario, cpf, cnpj);
			return "Usuário deletado com sucesso";
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao deletar: " + e.getMessage();
		}
	}
}
