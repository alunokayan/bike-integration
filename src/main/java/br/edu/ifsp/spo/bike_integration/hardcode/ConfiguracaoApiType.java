package br.edu.ifsp.spo.bike_integration.hardcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConfiguracaoApiType {
	BRASIL_API("BRASIL_API", "/cep/v2/");
	
	private String nome;
	private String endpoint;
}
