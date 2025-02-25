package br.edu.ifsp.spo.bike_integration.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifsp.spo.bike_integration.dto.EventoDto;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto;
import br.edu.ifsp.spo.bike_integration.hardcode.PaginationType;
import br.edu.ifsp.spo.bike_integration.model.Evento;
import br.edu.ifsp.spo.bike_integration.repository.EventoRepository;
import br.edu.ifsp.spo.bike_integration.response.ListEventoResponse;
import br.edu.ifsp.spo.bike_integration.rest.service.OpenStreetMapApiService;
import br.edu.ifsp.spo.bike_integration.util.DateUtil;
import br.edu.ifsp.spo.bike_integration.util.FormatUtil;
import br.edu.ifsp.spo.bike_integration.util.GeoJsonUtilFactory;
import jakarta.transaction.Transactional;

@Service
public class EventoService {

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private TipoEventoService tipoEventoService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private OpenStreetMapApiService openStreetMapApiService;

	public Evento buscarEvento(Long id) {
		return eventoRepository.findById(id).orElse(null);
	}

	public GeoJsonDto buscarEventoAsGeoJsonById(Long id) throws NotFoundException {
		return GeoJsonUtilFactory
				.convertEventosToGeoJson(this.eventoRepository.findById(id).orElseThrow(NotFoundException::new));
	}

	public List<Evento> buscarEventosByRadius(Double latitude, Double longitude, Double raio) {
		return this.getEventosProximosByLocation(latitude, longitude, raio);
	}

	public ListEventoResponse listarEventos(Long pagina, String nome, String descricao, String data, String cidade,
			String estado, Long faixaKm, Long tipoEvento, Long nivelHabilidade, Boolean gratuito) {

		Long limit = PaginationType.RESULTS_PER_PAGE.getValue();

		Long offset = (pagina - 1) * limit;

		String dataAjustada = DateUtil.fixFormattDate(data);

		List<Evento> eventos = eventoRepository.findAll(limit, offset, nome, descricao, dataAjustada, cidade, estado,
				faixaKm, tipoEvento, nivelHabilidade, gratuito);

		Long count = eventoRepository.countAll(nome, descricao, dataAjustada, cidade, estado, faixaKm, tipoEvento,
				nivelHabilidade, gratuito);

		Long totalPaginas = (long) Math.ceil(count / (double) limit);

		return ListEventoResponse.builder().eventos(eventos).totalRegistros(count).totalPaginas(totalPaginas).build();
	}

	public Evento createEvento(EventoDto eventoDto) {
		Map<String, Double> coordenadas = openStreetMapApiService
				.buscarCoordenadasPorEndereco(FormatUtil.formatEnderecoToOpenStreetMapApi(eventoDto.getEndereco()));
		eventoDto.getEndereco().setLatitude(coordenadas.get("lat"));
		eventoDto.getEndereco().setLongitude(coordenadas.get("lon"));

		return eventoRepository.save(Evento.builder().nome(eventoDto.getNome()).descricao(eventoDto.getDescricao())
				.data(eventoDto.getData()).dtAtualizacao(eventoDto.getDataAtualizacao())
				.endereco(eventoDto.getEndereco()).faixaKm(eventoDto.getFaixaKm()).gratuito(eventoDto.getGratuito())
				.tipoEvento(tipoEventoService.loadTipoEvento(eventoDto.getIdTipoEvento()))
				.usuario(usuarioService.loadUsuarioById(eventoDto.getIdUsuario())).build());
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
			evento.setUsuario(usuarioService.loadUsuarioById(eventoDto.getIdUsuario()));
			evento.setFaixaKm(eventoDto.getFaixaKm());
			evento.setGratuito(eventoDto.getGratuito());

			return eventoRepository.save(evento);
		}
		return null;
	}

	public void deleteEvento(Long id) {
		eventoRepository.deleteById(id);
	}

	@Transactional
	public void updateFotoEvento(Long id, MultipartFile file) {
		try {
			eventoRepository.saveFoto(id, file.getBytes());
		} catch (IOException e) {
			throw new RuntimeException("Erro ao atualizar foto do evento.");
		}
	}

	/*
	 * PRIVATE METHODS
	 */

	private List<Evento> getEventosProximosByLocation(Double latitude, Double longitude, Double raio) {
		return eventoRepository.findEventosProximosByLocation(latitude, longitude, raio);
	}
}
