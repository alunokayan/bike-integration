package br.edu.ifsp.spo.bike_integration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.dto.AvaliacaoDto;
import br.edu.ifsp.spo.bike_integration.model.AvaliacaoInfraestruturaCicloviaria;
import br.edu.ifsp.spo.bike_integration.service.AvaliacaoInfraestruturaCicloviariaService;
import br.edu.ifsp.spo.bike_integration.service.InfraestruturaCicloviariaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/avaliacao")
@Tag(name = "AvaliacaoInfraestruturaCicloviaria", description = "Controller para operações relacionadas a avaliações de infraestrutura cicloviária.")
public class AvaliacaoInfraestruturaCicloviariaController {

	@Autowired
	private AvaliacaoInfraestruturaCicloviariaService avaliacaoInfraestruturaCicloviariaService;

	@Autowired
	private InfraestruturaCicloviariaService infraestruturaCicloviariaService;

	@PostMapping("/do")
	@Operation(summary = "Avalia um trecho.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Trecho avaliado com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao avaliar trecho.") })
	public ResponseEntity<Void> avaliar(@RequestBody AvaliacaoDto avaliacao) {
		Integer nota = avaliacaoInfraestruturaCicloviariaService.avaliar(avaliacao);
		infraestruturaCicloviariaService.atualizarNota(avaliacao.getIdInfraestruturaCicloviaria(), nota);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/getAllByInfraestruturaCicloviariaId")
	@Operation(summary = "Recupera todas as avaliações de um trecho.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Avaliações recuperadas com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao recuperar avaliações.") })
	public ResponseEntity<List<AvaliacaoInfraestruturaCicloviaria>> getAllByInfraestruturaCicloviariaId(
			Long idInfraestruturaCicloviaria) {
		return ResponseEntity.ok(avaliacaoInfraestruturaCicloviariaService
				.getAllByInfraestruturaCicloviariaId(idInfraestruturaCicloviaria));
	}

}
