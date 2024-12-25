package br.edu.ifsp.spo.bike_integration.util;

import java.util.List;

import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto;
import br.edu.ifsp.spo.bike_integration.model.Evento;
import br.edu.ifsp.spo.bike_integration.model.InfraestruturaCicloviaria;
import br.edu.ifsp.spo.bike_integration.util.geojson.GeoJsonEventoUtil;
import br.edu.ifsp.spo.bike_integration.util.geojson.GeoJsonInfraestruturaUtil;

public class GeoJsonUtilFactory {

	private static GeoJsonEventoUtil eventoUtil = new GeoJsonEventoUtil();
	private static GeoJsonInfraestruturaUtil infraestruturaUtil = new GeoJsonInfraestruturaUtil();

	private GeoJsonUtilFactory() {
	}

	public static GeoJsonDto convertEventosToGeoJson(Evento evento) {
		return convertEventosToGeoJson(List.of(evento));
	}

	public static GeoJsonDto convertEventosToGeoJson(List<Evento> eventos) {
		return eventoUtil.convertToGeoJson(eventos);
	}

	public static GeoJsonDto convertInfraestruturaToGeoJson(InfraestruturaCicloviaria infraestrutura) {
		return convertInfraestruturasToGeoJson(List.of(infraestrutura));
	}

	public static GeoJsonDto convertInfraestruturasToGeoJson(List<InfraestruturaCicloviaria> infraestruturas) {
		return infraestruturaUtil.convertToGeoJson(infraestruturas);
	}
}
