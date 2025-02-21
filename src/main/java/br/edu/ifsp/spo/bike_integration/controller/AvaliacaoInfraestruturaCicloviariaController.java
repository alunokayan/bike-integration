package br.edu.ifsp.spo.bike_integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.dto.AvaliacaoDto;
import br.edu.ifsp.spo.bike_integration.service.AvaliacaoInfraestruturaCicloviariaService;
import br.edu.ifsp.spo.bike_integration.service.InfraestruturaCicloviariaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/v1/avaliacao")
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

}
