package br.edu.ifsp.spo.bike_integration.hardcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BrasilApiType {
	CEP("/cep/v2/"), 
	CNPJ("/cnpj/v1/");
	
	private String endpoint;
}
