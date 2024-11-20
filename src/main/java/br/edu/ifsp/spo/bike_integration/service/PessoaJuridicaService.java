package br.edu.ifsp.spo.bike_integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.model.PessoaJuridica;
import br.edu.ifsp.spo.bike_integration.repository.PessoaJuridicaRepository;
import br.edu.ifsp.spo.bike_integration.util.FormatUtil;

@Service
public class PessoaJuridicaService {

	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;

	public PessoaJuridica getPessoaJuridicaByCnpj(String cnpj) {
		return pessoaJuridicaRepository.findByCnpj(cnpj)
				.orElse(pessoaJuridicaRepository.findByCnpj(FormatUtil.formatCnpj(cnpj)).orElse(null));
	}

}
