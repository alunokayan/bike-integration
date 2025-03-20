package br.edu.ifsp.spo.bike_integration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.annotation.BearerAuthentication;
import br.edu.ifsp.spo.bike_integration.annotation.Role;
import br.edu.ifsp.spo.bike_integration.dto.TipoEventoDTO;
import br.edu.ifsp.spo.bike_integration.hardcode.RoleType;
import br.edu.ifsp.spo.bike_integration.model.TipoEvento;
import br.edu.ifsp.spo.bike_integration.service.TipoEventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("v1/tipo/evento")
@Tag(name = "Tipo Evento", description = "Controller para operações relacionadas a tipos de eventos.")
public class TipoEventoController {

	@Autowired
	private TipoEventoService tipoEventoService;

	@Role({ RoleType.PF, RoleType.PJ })
	@BearerAuthentication
	@GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Lista todos os tipos de eventos cadastrados.")
	public List<TipoEvento> listarTiposEventos() {
		return tipoEventoService.listarTiposEventos();
	}

	@Role(RoleType.ADMIN)
	@BearerAuthentication
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Cadastra um novo tipo de evento.")
	public ResponseEntity<TipoEvento> cadastrarTipoEvento(@RequestBody TipoEventoDTO tipoEvento) {
		return ResponseEntity.status(HttpStatus.CREATED).body(tipoEventoService.cadastrarTipoEvento(tipoEvento));
	}

}
