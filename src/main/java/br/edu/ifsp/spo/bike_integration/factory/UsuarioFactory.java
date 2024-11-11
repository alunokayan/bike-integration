package br.edu.ifsp.spo.bike_integration.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifsp.spo.bike_integration.builder.UsuarioBuilder;
import br.edu.ifsp.spo.bike_integration.dto.UsuarioDto;
import br.edu.ifsp.spo.bike_integration.exception.CryptoException;
import br.edu.ifsp.spo.bike_integration.model.Usuario;

@Component
public class UsuarioFactory {

    @Autowired
    private UsuarioBuilder usuarioBuilder;

    public Usuario fromDto(UsuarioDto usuarioDto) throws CryptoException {
        return usuarioBuilder.build(
            usuarioDto.getNome(),
            usuarioDto.getNomeUsuario(),
            usuarioDto.getIdPerfil(),
            usuarioDto.getCpf(),
            usuarioDto.getCnpj(),
            usuarioDto.getEndereco(),
            usuarioDto.getEmail(),
            usuarioDto.getSenha(),
            usuarioDto.getIdTipoUsuario()
        );
    }
}