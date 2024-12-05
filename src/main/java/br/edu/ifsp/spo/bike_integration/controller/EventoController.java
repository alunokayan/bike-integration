package br.edu.ifsp.spo.bike_integration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.dto.EventoDto;
import br.edu.ifsp.spo.bike_integration.model.Evento;
import br.edu.ifsp.spo.bike_integration.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1")
@Tag(name = "Evento", description = "Controller para operações relacionadas a eventos.")
public class EventoController {

	@Autowired
	private EventoService eventoService;

	@GetMapping("/eventos")
	@Operation(summary = "Lista todos os eventos cadastrados.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Eventos listados com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao listar eventos.") })
	public List<Evento> listarEventos() {
		return eventoService.listarEventos();
	}

	@PostMapping("/evento")
	@Operation(summary = "Cadastra um novo evento.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Evento cadastrado com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao cadastrar evento.") })
	public Evento cadastrarEvento(@RequestBody EventoDto evento){
		return eventoService.cadastrarEvento(evento);
	}
}
