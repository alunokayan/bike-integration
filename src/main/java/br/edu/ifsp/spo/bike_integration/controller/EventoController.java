package br.edu.ifsp.spo.bike_integration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifsp.spo.bike_integration.annotation.BearerToken;
import br.edu.ifsp.spo.bike_integration.annotation.Role;
import br.edu.ifsp.spo.bike_integration.dto.EventoDTO;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDTO;
import br.edu.ifsp.spo.bike_integration.hardcode.RoleType;
import br.edu.ifsp.spo.bike_integration.model.Evento;
import br.edu.ifsp.spo.bike_integration.response.ListEventoResponse;
import br.edu.ifsp.spo.bike_integration.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("v1/evento")
@Tag(name = "Evento", description = "Controller para operações relacionadas a eventos.")
public class EventoController {

	@Autowired
	private EventoService eventoService;

	@Role({ RoleType.PF, RoleType.PJ })
	@BearerToken
	@GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Lista todos os eventos cadastrados, limitando a um numero definido de resultados por pesquisa.")
	public ResponseEntity<ListEventoResponse> listarEventos(@RequestParam(required = true) Long pagina,
			@RequestParam(required = false) String nome, @RequestParam(required = false) String descricao,
			@RequestParam(required = false) String data, @RequestParam(required = false) String cidade,
			@RequestParam(required = false) String estado, @RequestParam(required = false) Long faixaKm,
			@RequestParam(required = false) Long tipoEvento, @RequestParam(required = false) Long nivelHabilidade,
			@RequestParam(required = false) Boolean gratuito) {
		return ResponseEntity.ok(eventoService.listarEventos(pagina, nome, descricao, data, cidade, estado, faixaKm,
				tipoEvento, nivelHabilidade, gratuito));
	}

	@Role(RoleType.PJ)
	@BearerToken
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Cadastra um novo evento.")
	public ResponseEntity<Evento> cadastrarEvento(@RequestBody EventoDTO evento) {
		return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.createEvento(evento));
	}

	@Role(RoleType.PJ)
	@BearerToken
	@PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Atualiza um evento.")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarEvento(@RequestParam(required = true) Long id, @RequestBody EventoDTO evento) {
		eventoService.updateEvento(id, evento);
	}

	@Role(RoleType.PJ)
	@BearerToken
	@PutMapping(path = "/update/foto/evento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Atualiza a foto de um evento.")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarFotoEvento(@RequestParam(required = true) Long id,
			@RequestParam(required = true) MultipartFile file) {
		eventoService.updateFotoEvento(id, file);
	}

	@Role(RoleType.ADMIN)
	@BearerToken
	@DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Deleta um evento.")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarEvento(@RequestParam(required = true) Long id) {
		eventoService.deleteEvento(id);
	}

	@Role({ RoleType.PF, RoleType.PJ })
	@BearerToken
	@GetMapping(path = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Busca um evento pelo id.")
	public ResponseEntity<Evento> buscarEvento(@RequestParam(required = true) Long id) {
		return ResponseEntity.ok(eventoService.buscarEvento(id));
	}

	@Role({ RoleType.PF, RoleType.PJ })
	@BearerToken
	@GetMapping(path = "/get/as/geoJson", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Busca um evento pelo id e retorna como GeoJson.")
	public ResponseEntity<GeoJsonDTO> buscarEventoAsGeoJson(@RequestParam(required = false) Long id)
			throws NotFoundException {
		return ResponseEntity.ok(eventoService.buscarEventoAsGeoJsonById(id));
	}

	@Role({ RoleType.PF, RoleType.PJ })
	@BearerToken
	@GetMapping(path = "/list/by/radius", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Busca eventos no raio estipulado com base na latitude e longitude informada.")
	public ResponseEntity<List<Evento>> buscarEventosAsGeoJson(@RequestParam(required = true) Double latitude,
			@RequestParam(required = true) Double longitude, @RequestParam(required = true) Double raio) {
		return ResponseEntity.ok(eventoService.buscarEventosByRadius(latitude, longitude, raio));
	}
}
