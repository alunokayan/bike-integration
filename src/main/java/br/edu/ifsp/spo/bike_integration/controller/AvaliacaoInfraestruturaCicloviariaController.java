package br.edu.ifsp.spo.bike_integration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.annotation.BearerToken;
import br.edu.ifsp.spo.bike_integration.annotation.Role;
import br.edu.ifsp.spo.bike_integration.dto.AvaliacaoDTO;
import br.edu.ifsp.spo.bike_integration.hardcode.RoleType;
import br.edu.ifsp.spo.bike_integration.model.AvaliacaoInfraestruturaCicloviaria;
import br.edu.ifsp.spo.bike_integration.service.AvaliacaoInfraestruturaCicloviariaService;
import br.edu.ifsp.spo.bike_integration.service.InfraestruturaCicloviariaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/avaliacao")
@Tag(name = "Avaliação Infraestrutura Cicloviária", description = "Controller para operações relacionadas a avaliações de infraestrutura cicloviária.")
public class AvaliacaoInfraestruturaCicloviariaController {

	@Autowired
	private AvaliacaoInfraestruturaCicloviariaService avaliacaoInfraestruturaCicloviariaService;

	@Autowired
	private InfraestruturaCicloviariaService infraestruturaCicloviariaService;

	@Role(RoleType.PF)
	@BearerToken
	@PostMapping(path = "/avaliar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Avalia um trecho de infraestrutura cicloviária e atualiza a nota do trecho.")
	@ResponseStatus(HttpStatus.CREATED)
	public void avaliar(@RequestBody AvaliacaoDTO avaliacao) {
		Integer nota = avaliacaoInfraestruturaCicloviariaService.avaliar(avaliacao);
		infraestruturaCicloviariaService.atualizarNota(avaliacao.getIdInfraestruturaCicloviaria(), nota);
	}

	@Role(RoleType.PF)
	@BearerToken
	@GetMapping(path = "/infraestrutura/{idInfraestruturaCicloviaria}/avaliacoes", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Recupera todas as avaliações de uma infraestrutura cicloviária.")
	public ResponseEntity<List<AvaliacaoInfraestruturaCicloviaria>> getAllByInfraestruturaCicloviariaId(
			@PathVariable("idInfraestruturaCicloviaria") Long idInfraestruturaCicloviaria) {
		List<AvaliacaoInfraestruturaCicloviaria> avaliacoes = avaliacaoInfraestruturaCicloviariaService
				.getAllByInfraestruturaCicloviariaId(idInfraestruturaCicloviaria);
		return ResponseEntity.ok(avaliacoes);
	}

}