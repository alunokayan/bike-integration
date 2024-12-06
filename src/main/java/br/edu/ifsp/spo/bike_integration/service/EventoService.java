package br.edu.ifsp.spo.bike_integration.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.dto.EventoDto;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto.FeatureDto;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto.GeometryDto;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto.PropertiesDto;
import br.edu.ifsp.spo.bike_integration.model.Evento;
import br.edu.ifsp.spo.bike_integration.repository.EventoRepository;
import br.edu.ifsp.spo.bike_integration.response.BrasilApiCepResponse.Location.Coordinates;

@Service
public class EventoService {

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private TipoEventoService tipoEventoService;

	@Autowired
	private BrasilApiService brasilApiService;

	public Evento buscarEvento(Long id) {
		return eventoRepository.findById(id).orElse(null);
	}

	public GeoJsonDto buscarEventoAsGeoJsonById(Long id) throws NotFoundException {
		return this.convertEventoToGeoJson(this.eventoRepository.findById(id).orElseThrow(NotFoundException::new));
	}

	public GeoJsonDto buscarEventosAsGeoJson(Double latitude, Double longitude, Double raio) {
		return this.convertEventosToGeoJson(this.getEventosProximosByLocation(latitude, longitude, raio));
	}

	public List<Evento> listarEventos() {
		return eventoRepository.findAll();
	}

	public Evento cadastrarEvento(EventoDto eventoDto) throws NotFoundException {
		Coordinates coordenadas = brasilApiService.buscarEnderecoPorCep(eventoDto.getEndereco().getCep()).getLocation()
				.getCoordinates();
		eventoDto.getEndereco().setLatitude(coordenadas.getLatitude());
		eventoDto.getEndereco().setLongitude(coordenadas.getLongitude());
		
		return eventoRepository.save(
				Evento.builder().nome(eventoDto.getNome()).descricao(eventoDto.getDescricao()).data(eventoDto.getData())
						.dtAtualizacao(eventoDto.getDataAtualizacao()).endereco(eventoDto.getEndereco())
						.tipoEvento(tipoEventoService.loadTipoEvento(eventoDto.getIdTipoEvento())).build());
	}

	/**
	 * 
	 * PRIVATE METHODS
	 * 
	 * @throws NotFoundException
	 * 
	 */

	private GeoJsonDto convertEventoToGeoJson(Evento evento) {
		// Properties
		PropertiesDto properties = PropertiesDto.builder().id(evento.getId()).type("evento").build();

		// Coordinates
		GeometryDto geometry = GeometryDto.builder().type("Point")
				.coordinates(List.of(List.of(List.of(evento.getEndereco().getLongitude(), evento.getEndereco().getLatitude())))).build();

		// Feature
		FeatureDto feature = FeatureDto.builder().type("Feature").id(evento.getId()).properties(properties)
				.geometry(geometry).build();

		// GeoJson
		return GeoJsonDto.builder().type("FeatureCollection").features(List.of(feature)).build();
	}

	private GeoJsonDto convertEventosToGeoJson(List<Evento> eventos) {
		List<FeatureDto> features = new ArrayList<>();
		for (Evento e : eventos) {
			// Properties
			PropertiesDto properties = PropertiesDto.builder().id(e.getId()).type("evento").build();

			// Coordinates
			GeometryDto geometry = GeometryDto.builder().type("Point")
					.coordinates(List.of(List.of(List.of(e.getEndereco().getLongitude(), e.getEndereco().getLatitude()))))
					.build();

			// Feature
			FeatureDto feature = FeatureDto.builder().type("Feature").id(e.getId()).properties(properties)
					.geometry(geometry).build();

			features.add(feature);
		}
		return GeoJsonDto.builder().type("FeatureCollection").features(features).build();
	}

	private List<Evento> getEventosProximosByLocation(Double latitude, Double longitude, Double raio) {
		return eventoRepository.findEventosProximosByLocation(latitude, longitude, raio);
	}

}
