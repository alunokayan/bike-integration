package br.edu.ifsp.spo.bike_integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.dto.ProblemaDTO;
import br.edu.ifsp.spo.bike_integration.model.Problema;
import br.edu.ifsp.spo.bike_integration.repository.ProblemaRepository;

@Service
public class ProblemaService {

	@Autowired
	private ProblemaRepository problemaRepository;

	@Autowired
	private TrechoService trechoService;

	public void registrar(ProblemaDTO problema) {
		problemaRepository.save(Problema.builder().descricao(problema.getDescricao()).validado(false).ativo(true)
				.trecho(trechoService.findById(problema.getIdTrecho())).build());
	}

}
