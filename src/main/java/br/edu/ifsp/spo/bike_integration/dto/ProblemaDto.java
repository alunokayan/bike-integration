package br.edu.ifsp.spo.bike_integration.dto;

import java.util.Date;

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
public class ProblemaDto {

	@Schema(example = "Buraco na pista")
	private String descricao;

	@Hidden
	private Date dtCriacao;

	@Hidden
	private Boolean validado;

	@Hidden
	private Boolean ativo;

	@Schema(example = "1")
	private Long idTrecho;
}
