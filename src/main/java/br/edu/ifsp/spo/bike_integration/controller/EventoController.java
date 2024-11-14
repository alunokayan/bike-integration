package br.edu.ifsp.spo.bike_integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.dto.EventoDto;
import br.edu.ifsp.spo.bike_integration.service.EventoService;
import br.edu.ifsp.spo.bike_integration.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1")
@Tag(name = "Evento", description = "Controller para operações relacionadas a eventos.")
public class EventoController {

	@Autowired
	private EventoService eventoService;

	@Operation(description = "Cria um novo evento.")
	@PostMapping("/evento/create")
	public ResponseEntity<Object> create(@RequestBody EventoDto evento) {
		return ResponseUtil.createResponse("Evento criado com sucesso id: " + eventoService.create(evento).getId(),
				HttpStatus.CREATED);
	}

}
