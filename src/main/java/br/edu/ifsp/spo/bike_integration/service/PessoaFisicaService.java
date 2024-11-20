package br.edu.ifsp.spo.bike_integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.model.PessoaFisica;
import br.edu.ifsp.spo.bike_integration.repository.PessoaFisicaRepository;
import br.edu.ifsp.spo.bike_integration.util.FormatUtil;

@Service
public class PessoaFisicaService {

	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;

	public PessoaFisica getPessoaFisicaByCpf(String cpf) {
		return pessoaFisicaRepository.findByCpf(cpf)
				.orElse(pessoaFisicaRepository.findByCpf(FormatUtil.formatCpf(cpf)).orElse(null));
	}

}
