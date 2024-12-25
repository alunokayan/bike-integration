package br.edu.ifsp.spo.bike_integration.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.dto.EventoDto;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto;
import br.edu.ifsp.spo.bike_integration.model.Evento;
import br.edu.ifsp.spo.bike_integration.repository.EventoRepository;
import br.edu.ifsp.spo.bike_integration.util.FormatUtil;
import br.edu.ifsp.spo.bike_integration.util.GeoJsonUtilFactory;

@Service
public class EventoService {

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private TipoEventoService tipoEventoService;

	@Autowired
	private OpenStreetMapApiService openStreetMapApiService;

	public Evento buscarEvento(Long id) {
		return eventoRepository.findById(id).orElse(null);
	}

	public GeoJsonDto buscarEventoAsGeoJsonById(Long id) throws NotFoundException {
		return GeoJsonUtilFactory
				.convertEventosToGeoJson(this.eventoRepository.findById(id).orElseThrow(NotFoundException::new));
	}

	public GeoJsonDto buscarEventosAsGeoJson(Double latitude, Double longitude, Double raio) {
		return GeoJsonUtilFactory.convertEventosToGeoJson(this.getEventosProximosByLocation(latitude, longitude, raio));
	}

	public List<Evento> listarEventos() {
		return eventoRepository.findAll();
	}

	public Evento createEvento(EventoDto eventoDto) {
		Map<String, Double> coordenadas = openStreetMapApiService
				.buscarCoordenadasPorEndereco(FormatUtil.formatEnderecoToOpenStreetMapApi(eventoDto.getEndereco()));
		eventoDto.getEndereco().setLatitude(coordenadas.get("lat"));
		eventoDto.getEndereco().setLongitude(coordenadas.get("lon"));

		return eventoRepository.save(
				Evento.builder().nome(eventoDto.getNome()).descricao(eventoDto.getDescricao()).data(eventoDto.getData())
						.dtAtualizacao(eventoDto.getDataAtualizacao()).endereco(eventoDto.getEndereco())
						.tipoEvento(tipoEventoService.loadTipoEvento(eventoDto.getIdTipoEvento())).build());
	}

	public Evento updateEvento(Long id, EventoDto eventoDto) {
		Evento evento = eventoRepository.findById(id).orElse(null);
		if (evento != null) {
			Map<String, Double> coordenadas = openStreetMapApiService
					.buscarCoordenadasPorEndereco(FormatUtil.formatEnderecoToOpenStreetMapApi(eventoDto.getEndereco()));
			eventoDto.getEndereco().setLatitude(coordenadas.get("lat"));
			eventoDto.getEndereco().setLongitude(coordenadas.get("lon"));

			evento.setNome(eventoDto.getNome());
			evento.setDescricao(eventoDto.getDescricao());
			evento.setData(eventoDto.getData());
			evento.setDtAtualizacao(eventoDto.getDataAtualizacao());
			evento.setEndereco(eventoDto.getEndereco());
			evento.setTipoEvento(tipoEventoService.loadTipoEvento(eventoDto.getIdTipoEvento()));

			return eventoRepository.save(evento);
		}
		return null;
	}

	public void deleteEvento(Long id) {
		eventoRepository.deleteById(id);
	}

	/*
	 * PRIVATE METHODS
	 */

	private List<Evento> getEventosProximosByLocation(Double latitude, Double longitude, Double raio) {
		return eventoRepository.findEventosProximosByLocation(latitude, longitude, raio);
	}
}
