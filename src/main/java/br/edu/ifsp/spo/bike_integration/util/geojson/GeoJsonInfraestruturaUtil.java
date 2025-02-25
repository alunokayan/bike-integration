package br.edu.ifsp.spo.bike_integration.util.geojson;

import java.util.List;

import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto.FeatureDto;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto.GeometryDto;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto.PropertiesDto;
import br.edu.ifsp.spo.bike_integration.model.InfraestruturaCicloviaria;
import br.edu.ifsp.spo.bike_integration.util.GeoJsonUtil;

public class GeoJsonInfraestruturaUtil implements GeoJsonUtil<List<InfraestruturaCicloviaria>> {

	@Override
	public GeoJsonDto convertToGeoJson(List<InfraestruturaCicloviaria> vias) {
		List<FeatureDto> features = vias.parallelStream().map(this::createFeature).toList();

		return GeoJsonDto.builder().type("FeatureCollection").features(features).build();
	}

	/*
	 * PRIVATE METHODS
	 */

	private FeatureDto createFeature(InfraestruturaCicloviaria via) {
		// Properties
		PropertiesDto properties = PropertiesDto.builder().name(via.getNome()).id(via.getJsonId())
				.type(via.getTipoInfraestruturaCicloviaria().getNome()).build();

		// Coordinates
		List<?> coordinates = via.getTrechos().stream()
				.map(trecho -> List.of(trecho.getLongitude(), trecho.getLatitude())).toList();

		if (via.getGeometria().equals("Polygon")) {
			coordinates = List.of(coordinates);
		}

		// Geometry
		GeometryDto geometry = GeometryDto.builder().type(via.getGeometria()).coordinates(coordinates).build();

		// Feature
		return FeatureDto.builder().type("Feature").id(via.getJsonId()).properties(properties).geometry(geometry)
				.build();
	}

}
