package br.edu.ifsp.spo.bike_integration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.dto.EventoDto;
import br.edu.ifsp.spo.bike_integration.model.Evento;
import br.edu.ifsp.spo.bike_integration.repository.EventoRepository;

@Service
public class EventoService {

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private TipoEventoService tipoEventoService;

	public List<Evento> listarEventos() {
		return eventoRepository.findAll();
	}

	public Evento cadastrarEvento(EventoDto eventoDto) {
		return eventoRepository.save(
				Evento.builder().nome(eventoDto.getNome()).descricao(eventoDto.getDescricao()).data(eventoDto.getData())
						.dtAtualizacao(eventoDto.getDataAtualizacao()).endereco(eventoDto.getEndereco())
						.tipoEvento(tipoEventoService.loadTipoEvento(eventoDto.getIdTipoEvento())).build());
	}

}
