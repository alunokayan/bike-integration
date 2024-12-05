package br.edu.ifsp.spo.bike_integration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.dto.TipoEventoDto;
import br.edu.ifsp.spo.bike_integration.model.TipoEvento;
import br.edu.ifsp.spo.bike_integration.service.TipoEventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1")
@Tag(name = "TipoEvento", description = "Controller para operações relacionadas a tipos de eventos.")
public class TipoEventoController {
	
	@Autowired
	private TipoEventoService tipoEventoService;
	
	@GetMapping("/tipos-eventos")
	@Operation(summary = "Lista todos os tipos de eventos cadastrados.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Tipos de eventos listados com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro ao listar tipos de eventos.") })
	public List<TipoEvento> listarTiposEventos() {
		return tipoEventoService.listarTiposEventos();
    }
	
	@PostMapping("/tipo-evento")
	@Operation(summary = "Cadastra um novo tipo de evento.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Tipo de evento cadastrado com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro ao cadastrar tipo de evento.") })
	public TipoEvento cadastrarTipoEvento(@RequestBody TipoEventoDto tipoEvento) {
		return tipoEventoService.cadastrarTipoEvento(tipoEvento);
	}

}
