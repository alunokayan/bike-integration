package br.edu.ifsp.spo.bike_integration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.model.TipoProblema;
import br.edu.ifsp.spo.bike_integration.repository.TipoProblemaRepository;

@Service
public class TipoProblemaService {

    @Autowired
    private TipoProblemaRepository tipoProblemaRepository;

    public TipoProblema loadTipoProblema(Long id) {
        return tipoProblemaRepository.findById(id).orElse(null);
    }

    public List<TipoProblema> listarTiposProblemas() {
        return tipoProblemaRepository.findAll();
    }

}
