package br.edu.ifsp.spo.bike_integration.util;

import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto;

public interface GeoJsonUtil<T> {
	public GeoJsonDto convertToGeoJson(T object);
}
