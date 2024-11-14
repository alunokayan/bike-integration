package br.edu.ifsp.spo.bike_integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.model.TipoEvento;
import br.edu.ifsp.spo.bike_integration.repository.TipoEventoRepository;

@Service
public class TipoEventoService {

	@Autowired
	private TipoEventoRepository tipoEventoRepository;
	
	public TipoEvento getById(Long id) {
	return tipoEventoRepository.findById(id)
		.orElseThrow(() -> new RuntimeException("Tipo de evento não encontrado"));
	}
}
