package br.edu.ifsp.spo.bike_integration.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.dto.AvaliacaoDto;
import br.edu.ifsp.spo.bike_integration.model.AvaliacaoInfraestruturaCicloviaria;
import br.edu.ifsp.spo.bike_integration.repository.AvaliacaoInfraestruturaCicloviariaRepository;

@Service
public class AvaliacaoInfraestruturaCicloviariaService {

	@Autowired
	private AvaliacaoInfraestruturaCicloviariaRepository avaliacaoInfraestruturaCicloviariaRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private InfraestruturaCicloviariaService infraestruturaService;

	public Integer avaliar(AvaliacaoDto avaliacao) {

		// Pre save
		AvaliacaoInfraestruturaCicloviaria avaliacaoToSave = AvaliacaoInfraestruturaCicloviaria.builder()
				.usuario(usuarioService.loadUsuarioById(avaliacao.getIdUsuario()))
				.infraestruturaCicloviaria(infraestruturaService.findById(avaliacao.getIdInfraestruturaCicloviaria()))
				.nota(avaliacao.getNota()).comentario(avaliacao.getComentario()).dtCriacao(new Date()).build();

		// Add pre save to others
		List<AvaliacaoInfraestruturaCicloviaria> infraestruturas = avaliacaoInfraestruturaCicloviariaRepository
				.findAllByInfraestruturaCicloviariaId(avaliacao.getIdInfraestruturaCicloviaria());
		infraestruturas.add(avaliacaoToSave);

		// Save
		avaliacaoInfraestruturaCicloviariaRepository.save(avaliacaoToSave);

		return (int) infraestruturas.stream().mapToDouble(AvaliacaoInfraestruturaCicloviaria::getNota).average()
				.orElse(0.0);

	}

}
