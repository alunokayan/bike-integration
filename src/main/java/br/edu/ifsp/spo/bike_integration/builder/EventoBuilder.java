package br.edu.ifsp.spo.bike_integration.builder;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifsp.spo.bike_integration.dto.EnderecoDto;
import br.edu.ifsp.spo.bike_integration.model.Endereco;
import br.edu.ifsp.spo.bike_integration.model.Evento;
import br.edu.ifsp.spo.bike_integration.service.TipoEventoService;
import br.edu.ifsp.spo.bike_integration.service.UsuarioService;

@Component
public class EventoBuilder {

	@Autowired
	private TipoEventoService tipoEventoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public Evento build(String nome, String descricao, Date dataInicial, Date dataFinal,
			Boolean gratuito, Long idTipoEvento, EnderecoDto endereco, String usuarioResponsavel) {
		return Evento.builder()
				.nome(nome)
				.descricao(descricao)
				.dataInicial(dataInicial)
				.dataFinal(dataFinal)
				.gratuito(gratuito)
				.tipoEvento(tipoEventoService.getById(idTipoEvento))
				.endereco(Endereco.builder()
		                .rua(endereco.getRua())
		                .numero(endereco.getNumero())
		                .complemento(endereco.getComplemento())
		                .bairro(endereco.getBairro())
		                .cidade(endereco.getCidade())
		                .estado(endereco.getEstado())
		                .cep(endereco.getCep())
		                .build())
				.usuario(usuarioService.getByNomeUsuario(usuarioResponsavel))
			.build();
	}

}
