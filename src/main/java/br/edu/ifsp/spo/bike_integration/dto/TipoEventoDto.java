package br.edu.ifsp.spo.bike_integration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoEventoDto {
	
	private String nome;
	private Long nivelHabilidade;
}
