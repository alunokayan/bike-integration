package br.edu.ifsp.spo.bike_integration.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifsp.spo.bike_integration.aws.service.S3Service;
import br.edu.ifsp.spo.bike_integration.dto.EventoDTO;
import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDTO;
import br.edu.ifsp.spo.bike_integration.hardcode.PaginationType;
import br.edu.ifsp.spo.bike_integration.model.Evento;
import br.edu.ifsp.spo.bike_integration.model.Usuario;
import br.edu.ifsp.spo.bike_integration.repository.EventoRepository;
import br.edu.ifsp.spo.bike_integration.response.ListEventoResponse;
import br.edu.ifsp.spo.bike_integration.rest.service.OpenStreetMapApiService;
import br.edu.ifsp.spo.bike_integration.util.DateUtil;
import br.edu.ifsp.spo.bike_integration.util.FormatUtil;
import br.edu.ifsp.spo.bike_integration.util.GeoJsonUtilFactory;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

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

	@Autowired
	private S3Service s3Service;

	@Value("${aws.s3.bucket-name}")
	private String bucketName;

	public Evento buscarEvento(Long id) {
		return eventoRepository.findById(id).orElse(null);
	}

	public GeoJsonDTO buscarEventoAsGeoJsonById(Long id) {
		return GeoJsonUtilFactory.convertEventosToGeoJson(this.buscarEvento(id));
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

	public Evento createEvento(EventoDTO eventoDto) {
		Map<String, Double> coordenadas = openStreetMapApiService
				.buscarCoordenadasPorEndereco(FormatUtil.formatEnderecoToOpenStreetMapApi(eventoDto.getEndereco()));
		eventoDto.getEndereco().setLatitude(coordenadas.get("lat"));
		eventoDto.getEndereco().setLongitude(coordenadas.get("lon"));

		Usuario usuario = usuarioService.loadUsuarioById(eventoDto.getIdUsuario());

		return eventoRepository.save(Evento.builder().nome(eventoDto.getNome()).descricao(eventoDto.getDescricao())
				.data(DateUtil.parseDate(eventoDto.getData())).dtAtualizacao(eventoDto.getDataAtualizacao())
				.endereco(eventoDto.getEndereco()).faixaKm(eventoDto.getFaixaKm()).gratuito(eventoDto.getGratuito())
				.tipoEvento(tipoEventoService.loadTipoEvento(eventoDto.getIdTipoEvento())).usuario(usuario).build());
	}

	public void updateEvento(Long id, EventoDTO eventoDto) {
		Evento evento = eventoRepository.findById(id).orElse(null);
		if (evento != null) {
			Map<String, Double> coordenadas = openStreetMapApiService
					.buscarCoordenadasPorEndereco(FormatUtil.formatEnderecoToOpenStreetMapApi(eventoDto.getEndereco()));
			eventoDto.getEndereco().setLatitude(coordenadas.get("lat"));
			eventoDto.getEndereco().setLongitude(coordenadas.get("lon"));

			Usuario usuario = usuarioService.loadUsuarioById(eventoDto.getIdUsuario());

			evento.setNome(eventoDto.getNome());
			evento.setDescricao(eventoDto.getDescricao());
			evento.setData(DateUtil.parseDate(eventoDto.getData()));
			evento.setDtAtualizacao(eventoDto.getDataAtualizacao());
			evento.setEndereco(eventoDto.getEndereco());
			evento.setTipoEvento(tipoEventoService.loadTipoEvento(eventoDto.getIdTipoEvento()));
			evento.setFaixaKm(eventoDto.getFaixaKm());
			evento.setGratuito(eventoDto.getGratuito());
			evento.setUsuario(usuario);

			eventoRepository.save(evento);
		}
	}

	public void deleteEvento(Long id) {
		Evento evento = eventoRepository.findById(id).orElse(null);
		if (evento != null) {
			eventoRepository.delete(evento);
		}
	}

	public void deleteEventosByUsuario(Long idUsuario) {
		Usuario usuario = usuarioService.loadUsuarioById(idUsuario);
		eventoRepository.deleteByUsuario(usuario);
	}

	public void updateFotoEvento(Long id, MultipartFile file) {
		try {
			Evento evento = eventoRepository.findById(id).orElse(null);
			if (evento != null) {
				s3Service.put(PutObjectRequest.builder().bucket(bucketName).key(createKeyFotoEvento(id))
						.contentType(file.getContentType()).build(), file.getBytes());
			}
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

	private String createKeyFotoEvento(Long id) {
		return "evento_" + id + "_" + System.currentTimeMillis();
	}
}
