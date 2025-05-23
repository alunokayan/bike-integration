package br.edu.ifsp.spo.bike_integration.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.dto.AvaliacaoDTO;
import br.edu.ifsp.spo.bike_integration.hardcode.PaginationType;
import br.edu.ifsp.spo.bike_integration.model.AvaliacaoInfraestruturaCicloviaria;
import br.edu.ifsp.spo.bike_integration.repository.AvaliacaoInfraestruturaCicloviariaRepository;
import br.edu.ifsp.spo.bike_integration.response.ListAvaliacoesResponse;

@Service
public class AvaliacaoInfraestruturaCicloviariaService {

	@Autowired
	private AvaliacaoInfraestruturaCicloviariaRepository avaliacaoInfraestruturaCicloviariaRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private InfraestruturaCicloviariaService infraestruturaService;

	public Integer avaliar(AvaliacaoDTO avaliacao) {

		// Pre save
		AvaliacaoInfraestruturaCicloviaria avaliacaoToSave = AvaliacaoInfraestruturaCicloviaria.builder()
				.usuario(usuarioService.loadUsuarioById(avaliacao.getIdUsuario()))
				.infraestruturaCicloviaria(infraestruturaService.findById(avaliacao.getIdInfraestruturaCicloviaria()))
				.nota(avaliacao.getNota()).comentario(avaliacao.getComentario()).dtCriacao(LocalDateTime.now()).build();

		// Add pre save to others
		List<AvaliacaoInfraestruturaCicloviaria> infraestruturas = avaliacaoInfraestruturaCicloviariaRepository
				.findAllByInfraestruturaCicloviariaId(avaliacao.getIdInfraestruturaCicloviaria());
		infraestruturas.add(avaliacaoToSave);

		// Save
		avaliacaoInfraestruturaCicloviariaRepository.save(avaliacaoToSave);

		return (int) infraestruturas.stream().mapToDouble(AvaliacaoInfraestruturaCicloviaria::getNota).average()
				.orElse(0.0);

	}

	public ListAvaliacoesResponse getAllByInfraestruturaCicloviariaIdAndNota(Long idInfraestruturaCicloviaria,
			Integer nota,
			Integer pagina) {
		Long limit = PaginationType.RESULTS_PER_PAGE.getValue();

		Long offset = (pagina - 1) * limit;

		List<AvaliacaoInfraestruturaCicloviaria> avaliacoes = avaliacaoInfraestruturaCicloviariaRepository
				.findAllByInfraestruturaCicloviariaIdAndNota(idInfraestruturaCicloviaria, nota, limit, offset);

		Long count = avaliacaoInfraestruturaCicloviariaRepository
				.countByInfraestruturaCicloviariaIdAndNota(idInfraestruturaCicloviaria, nota);

		Long totalPaginas = (long) Math.ceil(count / (double) limit);

		return ListAvaliacoesResponse.builder().avaliacoes(avaliacoes).totalRegistros(count)
				.totalPaginas(totalPaginas).build();
	}

}
