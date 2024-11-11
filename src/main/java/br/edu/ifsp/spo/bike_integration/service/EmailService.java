package br.edu.ifsp.spo.bike_integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.model.Email;
import br.edu.ifsp.spo.bike_integration.repository.EmailRepository;

@Service
public class EmailService {

	@Autowired
	private EmailRepository emailRepository;
	
	public Email getEmailByEndereco(String endereco) {
    return emailRepository.findByValor(endereco)
        .orElse(null);
    }
	
}
