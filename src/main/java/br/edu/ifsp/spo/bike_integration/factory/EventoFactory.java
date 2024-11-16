package br.edu.ifsp.spo.bike_integration.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifsp.spo.bike_integration.builder.EventoBuilder;
import br.edu.ifsp.spo.bike_integration.dto.EventoDto;
import br.edu.ifsp.spo.bike_integration.model.Evento;

@Component
public class EventoFactory {

	@Autowired
	private EventoBuilder eventoBuilder;
	
	public Evento fromDto(EventoDto evento) throws Exception {
		return eventoBuilder.build(
				evento.getNome(), 
				evento.getDescricao(), 
				evento.getDataInicial(), 
				evento.getDataFinal(),
				evento.getGratuito(), 
				evento.getIdTipoEvento(),
				evento.getEndereco(), 
				evento.getUsuarioResponsavel()
			);
    }
	
}
