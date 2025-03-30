package br.edu.ifsp.spo.bike_integration.factory;

import java.util.List;

import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDTO;
import br.edu.ifsp.spo.bike_integration.model.Evento;
import br.edu.ifsp.spo.bike_integration.model.InfraestruturaCicloviaria;
import br.edu.ifsp.spo.bike_integration.util.geojson.GeoJsonEventoUtils;
import br.edu.ifsp.spo.bike_integration.util.geojson.GeoJsonInfraestruturaUtils;

public class GeoJsonUtilFactory {

	private static GeoJsonEventoUtils eventoUtil = new GeoJsonEventoUtils();
	private static GeoJsonInfraestruturaUtils infraestruturaUtil = new GeoJsonInfraestruturaUtils();

	private GeoJsonUtilFactory() {
	}

	public static GeoJsonDTO convertEventosToGeoJson(Evento evento) {
		return convertEventosToGeoJson(List.of(evento));
	}

	public static GeoJsonDTO convertEventosToGeoJson(List<Evento> eventos) {
		return eventoUtil.convertToGeoJson(eventos);
	}

	public static GeoJsonDTO convertInfraestruturaToGeoJson(InfraestruturaCicloviaria infraestrutura) {
		return convertInfraestruturasToGeoJson(List.of(infraestrutura));
	}

	public static GeoJsonDTO convertInfraestruturasToGeoJson(List<InfraestruturaCicloviaria> infraestruturas) {
		return infraestruturaUtil.convertToGeoJson(infraestruturas);
	}
}
