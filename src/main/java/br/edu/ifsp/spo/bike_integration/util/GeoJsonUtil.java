package br.edu.ifsp.spo.bike_integration.util;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto.FeatureDto;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto.GeometryDto;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto.PropertiesDto;
import br.edu.ifsp.spo.bike_integration.model.Evento;

public interface GeoJsonUtil {

	public static GeoJsonDto convertToGeoJson(Object input) {
		List<FeatureDto> features = new ArrayList<>();

		if (input instanceof Evento evento) {
			features.add(createFeature(evento));
		} else if (input instanceof List<?> eventos) {
			for (Object obj : eventos) {
				if (obj instanceof Evento evento) {
					features.add(createFeature(evento));
				}
			}
		}

		return GeoJsonDto.builder().type("FeatureCollection").features(features).build();
	}

	private static FeatureDto createFeature(Evento evento) {
		// Properties
		PropertiesDto properties = PropertiesDto.builder().id(evento.getId()).type("evento").build();

		// Coordinates
		GeometryDto geometry = GeometryDto.builder().type("Point")
				.coordinates(List
						.of(List.of(List.of(evento.getEndereco().getLongitude(), evento.getEndereco().getLatitude()))))
				.build();

		// Feature
		return FeatureDto.builder().type("Feature").id(evento.getId()).properties(properties).geometry(geometry)
				.build();
	}
}
