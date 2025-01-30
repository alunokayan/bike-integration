package br.edu.ifsp.spo.bike_integration.hardcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ViaCepApiType {
	WS("/ws/");
	
	private String endpoint;
}
