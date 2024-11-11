package br.edu.ifsp.spo.bike_integration.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnderecoDto {
	@Schema(example = "Rua da Bicicleta")
	String rua;
	
	@Schema(example = "123")
	String numero;
	
	@Schema(example = "Casa 2")
	String complemento;
	
	@Schema(example = "Bairro da Bicicleta")
	String bairro;
	
	@Schema(example = "São Paulo")
	String cidade;
	
	@Schema(example = "SP")
	String estado;
	
	@Schema(example = "00000-000")
	String cep;
}
