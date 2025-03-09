package br.edu.ifsp.spo.bike_integration.util;

import java.util.List;

import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDTO;
import br.edu.ifsp.spo.bike_integration.model.Evento;
import br.edu.ifsp.spo.bike_integration.model.InfraestruturaCicloviaria;
import br.edu.ifsp.spo.bike_integration.util.geojson.GeoJsonEventoUtil;
import br.edu.ifsp.spo.bike_integration.util.geojson.GeoJsonInfraestruturaUtil;

public class GeoJsonUtilFactory {

	private static GeoJsonEventoUtil eventoUtil = new GeoJsonEventoUtil();
	private static GeoJsonInfraestruturaUtil infraestruturaUtil = new GeoJsonInfraestruturaUtil();

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
