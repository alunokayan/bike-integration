package br.edu.ifsp.spo.bike_integration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto;
import br.edu.ifsp.spo.bike_integration.model.InfraestruturaCicloviaria;
import br.edu.ifsp.spo.bike_integration.service.InfraestruturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/infraestrutura")
@Tag(name = "Infraestrutura", description = "API de Infraestrutura Cicloviária")
public class InfraestruturaController {

	@Autowired
	private InfraestruturaService infraestruturaService;

	@GetMapping("/list")
	@Operation(summary = "Listar todas as infraestruturas cicloviárias")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Infraestruturas cicloviárias listadas com sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor") })
	public ResponseEntity<List<InfraestruturaCicloviaria>> findAll() {
		return ResponseEntity.ok(infraestruturaService.findAll());
	}

	@GetMapping("/get")
	@Operation(summary = "Buscar uma infraestrutura cicloviária por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Infraestrutura cicloviária encontrada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Infraestrutura cicloviária não encontrada") })
	public ResponseEntity<InfraestruturaCicloviaria> findById(Long id) {
		return ResponseEntity.ok(infraestruturaService.findById(id));
	}

	@GetMapping("/getAsGeoJson")
	@Operation(summary = "Buscar uma infraestrutura cicloviária por ID no formato GeoJson")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Infraestrutura cicloviária encontrada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Infraestrutura cicloviária não encontrada") })
	public ResponseEntity<GeoJsonDto> buscarInfraestruturaAsGeoJsonById(Long id) throws NotFoundException {
		return ResponseEntity.ok(infraestruturaService.buscarInfraestruturaAsGeoJsonById(id));
	}

	@GetMapping("/listAsGeoJson")
	@Operation(summary = "Listar todas as infraestruturas cicloviárias no formato GeoJson")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Infraestruturas cicloviárias listadas com sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor") })
	public ResponseEntity<GeoJsonDto> buscarInfraestruturasAsGeoJson(Double latitude, Double longitude, Double raio) {
		return ResponseEntity.ok(infraestruturaService.buscarInfraestruturasAsGeoJson(latitude, longitude, raio));
	}

}
