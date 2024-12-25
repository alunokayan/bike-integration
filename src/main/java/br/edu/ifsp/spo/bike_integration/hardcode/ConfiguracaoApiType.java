package br.edu.ifsp.spo.bike_integration.hardcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConfiguracaoApiType {
	BRASIL_API("BRASIL_API"),
	OPEN_STREET_MAP_API("OPEN_STREET_MAP_API");
	
	private String nome;
}
