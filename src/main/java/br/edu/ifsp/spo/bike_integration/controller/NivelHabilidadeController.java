package br.edu.ifsp.spo.bike_integration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.dto.NivelHabilidadeDto;
import br.edu.ifsp.spo.bike_integration.model.NivelHabilidade;
import br.edu.ifsp.spo.bike_integration.service.NivelHabilidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1")
@Tag(name = "NivelHabilidade", description = "Controller para operações relacionadas a níveis de habilidade.")
public class NivelHabilidadeController {
	
	@Autowired
	private NivelHabilidadeService nivelHabilidadeService;
	
	@GetMapping("/niveis-habilidade")
	@Operation(summary = "Lista todos os níveis de habilidade cadastrados.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Níveis de habilidade listados com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro ao listar níveis de habilidade.") })
	public List<NivelHabilidade> listarNiveisHabilidade() {
		return nivelHabilidadeService.listarNiveisHabilidade();
	}
	
	@PostMapping("/nivel-habilidade")
	@Operation(summary = "Cadastra um novo nível de habilidade.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Nível de habilidade cadastrado com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro ao cadastrar nível de habilidade.") })
	public NivelHabilidade cadastrarNivelHabilidade(@RequestBody NivelHabilidadeDto nivelHabilidade) {
		return nivelHabilidadeService.cadastrarNivelHabilidade(nivelHabilidade);
	}

}
