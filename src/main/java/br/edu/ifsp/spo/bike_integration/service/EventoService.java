package br.edu.ifsp.spo.bike_integration.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.dto.EventoDto;
import br.edu.ifsp.spo.bike_integration.factory.EventoFactory;
import br.edu.ifsp.spo.bike_integration.model.Evento;
import br.edu.ifsp.spo.bike_integration.repository.EventoRepository;

@Service
public class EventoService {

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private EventoFactory eventoFactory;

	public Evento create(EventoDto evento) throws Exception {
		Optional<Evento> eventoFromDto = eventoRepository.findByNome(evento.getNome());

		if (eventoFromDto.isPresent() && evento.equalsEvento(eventoFromDto.get())) {
			throw new IllegalArgumentException("Evento já cadastrado.");
		}

		return eventoRepository.save(eventoFactory.fromDto(evento));
	}

}
