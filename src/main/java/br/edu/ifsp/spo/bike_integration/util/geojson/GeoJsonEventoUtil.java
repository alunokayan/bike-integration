package br.edu.ifsp.spo.bike_integration.util.geojson;

import java.util.List;

import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto.FeatureDto;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto.GeometryDto;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto.PropertiesDto;
import br.edu.ifsp.spo.bike_integration.model.Evento;
import br.edu.ifsp.spo.bike_integration.util.GeoJsonUtil;

public class GeoJsonEventoUtil implements GeoJsonUtil<List<Evento>> {

	@Override
	public GeoJsonDto convertToGeoJson(List<Evento> eventos) {
		List<FeatureDto> features = eventos.parallelStream().map(this::createFeature).toList();

		return GeoJsonDto.builder().type("FeatureCollection").features(features).build();
	}

	/*
	 * PRIVATE METHODS
	 */

	private FeatureDto createFeature(Evento evento) {
		// Properties
		PropertiesDto properties = PropertiesDto.builder().name(evento.getEndereco().getRua())
				.id(evento.getId().toString()).type("Evento").build();

		// Geometry
		GeometryDto geometry = GeometryDto.builder().type("Point")
				.coordinates(List.of(evento.getEndereco().getLongitude(), evento.getEndereco().getLatitude())).build();

		// Feature
		return FeatureDto.builder().type("Feature").id(evento.getId().toString()).properties(properties)
				.geometry(geometry).build();
	}
}
