package br.edu.ifsp.spo.bike_integration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.dto.GeoJsonDto;
import br.edu.ifsp.spo.bike_integration.model.InfraestruturaCicloviaria;
import br.edu.ifsp.spo.bike_integration.repository.InfraestruturaCicloviariaRepository;
import br.edu.ifsp.spo.bike_integration.util.GeoJsonUtilFactory;

@Service
public class InfraestruturaCicloviariaService {

	@Autowired
	private InfraestruturaCicloviariaRepository infraestruturaCicloviariaRepository;

	public InfraestruturaCicloviaria findById(Long id) {
		InfraestruturaCicloviaria infra = infraestruturaCicloviariaRepository.findById(id).orElse(null);
		infra.getTrechos().forEach(trecho -> {
			trecho.setInfraestruturaCicloviaria(null);
		});
		return infra;
	}

	public List<InfraestruturaCicloviaria> findInfraestruturasProximasByLocation(double latitude, double longitude,
			double raio) {
		return infraestruturaCicloviariaRepository.findInfraestruturasProximasByLocation(latitude, longitude, raio);
	}

	public GeoJsonDto buscarInfraestruturaAsGeoJsonById(Long id) throws NotFoundException {
		return GeoJsonUtilFactory.convertInfraestruturaToGeoJson(
				this.infraestruturaCicloviariaRepository.findById(id).orElseThrow(NotFoundException::new));
	}

	public GeoJsonDto buscarInfraestruturasAsGeoJson(Double latitude, Double longitude, Double raio) {
		return GeoJsonUtilFactory
				.convertInfraestruturasToGeoJson(this.getInfraestruturasProximasByLocation(latitude, longitude, raio));
	}

	public void atualizarNota(Long idInfraestruturaCicloviaria, Integer nota) {
		infraestruturaCicloviariaRepository.atualizarNota(idInfraestruturaCicloviaria, nota);
	}

	/*
	 * PRIVATE METHODS
	 */

	private List<InfraestruturaCicloviaria> getInfraestruturasProximasByLocation(Double latitude, Double longitude,
			Double raio) {
		return infraestruturaCicloviariaRepository.findInfraestruturasProximasByLocation(latitude, longitude, raio);
	}

}
