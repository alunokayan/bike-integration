package br.edu.ifsp.spo.bike_integration.hardcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OpenStreetMapApiType {
	SEARCH("/search"), 
	REVERSE("/reverse");
	
	private String endpoint;
}
