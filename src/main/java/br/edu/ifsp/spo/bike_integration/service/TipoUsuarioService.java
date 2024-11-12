package br.edu.ifsp.spo.bike_integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.model.TipoUsuario;
import br.edu.ifsp.spo.bike_integration.repository.TipoUsuarioRepository;

@Service
public class TipoUsuarioService {

	@Autowired
	private TipoUsuarioRepository tipoUsuarioRepository;
	
	public TipoUsuario getTipoUsuarioById(Long id) {
    return tipoUsuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Tipo de usuário não encontrado"));
	}
}
