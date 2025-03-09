package br.edu.ifsp.spo.bike_integration.util;

import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDTO;

public interface GeoJsonUtil<T> {
	public GeoJsonDTO convertToGeoJson(T object);
}
