package br.edu.ifsp.spo.bike_integration.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class ProblemaDTO {

	@Schema(example = "Buraco na pista")
	private String descricao;

	@Schema(example = "1")
	private Long idTipoProblema;

	@Hidden
	private LocalDateTime dtCriacao;

	@Schema(example = "false")
	private Boolean validado;

	@Schema(example = "true")
	private Boolean ativo;

	@Schema(example = "-23.123456")
	private Double latitude;

	@Schema(example = "-23.123456")
	private Double longitude;
}
