package br.edu.ifsp.spo.bike_integration.dto;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.edu.ifsp.spo.bike_integration.model.Evento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventoDto {

	@Schema(example = "Evento de exemplo")
	private String nome;

	@Schema(example = "Descrição do evento de exemplo")
	private String descricao;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    @Schema(example = "2024-11-13T10:00:00-03:00")
    private Date dataInicial;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    @Schema(example = "2024-11-13T10:00:00-03:00")
    private Date dataFinal;

	@Schema(example = "true")
	private Boolean gratuito;

	@Schema(example = "1")
	private Long idTipoEvento;

	private EnderecoDto endereco;

	@Schema(example = "usuario123", description = "Nome de usuário responsável pelo evento")
	private String usuarioResponsavel;

	@Schema(hidden = true)
	public boolean equalsEvento(Evento other) {
		if (other == null)
			return false;
		return compareDateValues(dataFinal, other.getDataFinal())
		        && compareDateValues(dataInicial, other.getDataInicial())
		        && Objects.equals(endereco.getCep(), other.getEndereco().getCep())
		        && Objects.equals(idTipoEvento, other.getTipoEvento().getId()) 
		        && Objects.equals(nome, other.getNome());
	}
	
	@Schema(hidden = true)
	public boolean compareDateValues(Date date1, Date date2) {
	    if (date1 == null || date2 == null) {
	        return false;
	    }
	    
	    Calendar cal1 = Calendar.getInstance();
	    cal1.setTime(date1);
	    
	    Calendar cal2 = Calendar.getInstance();
	    cal2.setTime(date2);
	    
	    return cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
	            && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
	            && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
	}
}
