package br.edu.ifsp.spo.bike_integration.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
	private String rua;

	@NotNull
	@Schema(example = "123")
	private Long numero;

	@Schema(example = "Casa 2")
	private String complemento;

	@Schema(example = "Bairro da Bicicleta")
	private String bairro;

	@Schema(example = "São Paulo")
	private String cidade;

	@Schema(example = "SP")
	private String estado;

	@Schema(example = "00000-000")
	private String cep;

	@Schema(hidden = true)
	private String latitude;

	@Schema(hidden = true)
	private String longitude;

	@Schema(hidden = true)
	public boolean hasSecondAtributes() {
		return rua != null && bairro != null && cidade != null && estado != null;
	}
}
