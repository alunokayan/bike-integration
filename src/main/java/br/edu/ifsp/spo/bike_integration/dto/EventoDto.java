package br.edu.ifsp.spo.bike_integration.dto;

import java.util.Date;

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
public class EventoDto {

	@Schema(example = "Evento de lançamento do novo produto")
	private String nome;
	
	@Schema(example = "Evento de lançamento do novo produto")
	private String descricao;
	
	@Schema(example = "2021-10-10T10:00:00.000")
	private Date data;
	
	@Hidden
	@Builder.Default
	private Date dataAtualizacao = new Date();
	
	private EnderecoDto endereco;
	
	@Schema(example = "10")
	private Long faixaKm;
	
	@Schema(example = "true")
	private Boolean gratuito;
	
	@Schema(example = "1")
	private Long idTipoEvento;
	
}
