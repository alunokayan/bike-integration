package br.edu.ifsp.spo.bike_integration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.dto.EventoDto;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto;
import br.edu.ifsp.spo.bike_integration.model.Evento;
import br.edu.ifsp.spo.bike_integration.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/evento")
@Tag(name = "Evento", description = "Controller para operações relacionadas a eventos.")
public class EventoController {

	@Autowired
	private EventoService eventoService;

	@GetMapping("/list")
	@Operation(summary = "Lista todos os eventos cadastrados.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Eventos listados com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao listar eventos.") })
	public List<Evento> listarEventos() {
		return eventoService.listarEventos();
	}

	@PostMapping("/create")
	@Operation(summary = "Cadastra um novo evento.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Evento cadastrado com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao cadastrar evento.") })
	public ResponseEntity<Evento> cadastrarEvento(@RequestBody EventoDto evento) {
		return ResponseEntity.status(201).body(eventoService.createEvento(evento));
	}
	
	@PutMapping("/update")
	@Operation(summary = "Atualiza um evento.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao atualizar evento.") })
	public Evento atualizarEvento(@RequestParam(name = "id", required = true) Long id, @RequestBody EventoDto evento) {
		return eventoService.updateEvento(id, evento);
	}
	
	@DeleteMapping("/delete")
	@Operation(summary = "Deleta um evento.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Evento deletado com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao deletar evento.") })
	public void deletarEvento(@RequestParam(name = "id", required = true) Long id) {
		eventoService.deleteEvento(id);
	}

	@GetMapping("/get")
	@Operation(summary = "Busca um evento pelo id.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Evento encontrado com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao buscar evento.") })
	public Evento buscarEvento(@RequestParam(name = "id", required = true) Long id) {
		return eventoService.buscarEvento(id);
	}

	@GetMapping("/getAsGeoJson")
	@Operation(summary = "Busca um evento pelo id e retorna como GeoJson.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Evento encontrado com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao buscar evento.") })
	public GeoJsonDto buscarEventoAsGeoJson(@RequestParam(name = "id", required = false) Long id)
			throws NotFoundException {
		return eventoService.buscarEventoAsGeoJsonById(id);
	}

	@GetMapping("/listAsGeoJson")
	@Operation(summary = "Busca eventos no raio estipulado com base na latitude e longitude informada.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Eventos encontrados com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao buscar eventos.") })
	public GeoJsonDto buscarEventosAsGeoJson(@RequestParam(name = "latitude", required = true) Double latitude,
			@RequestParam(name = "longitude", required = true) Double longitude,
			@RequestParam(name = "raio", required = true) Double raio) {
		return eventoService.buscarEventosAsGeoJson(latitude, longitude, raio);
	}
}
