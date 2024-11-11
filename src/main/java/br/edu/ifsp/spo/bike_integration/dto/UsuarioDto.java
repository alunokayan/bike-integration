package br.edu.ifsp.spo.bike_integration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDto {
	String nome;
//	PerfilDto perfil;
//	PessoaFisicaDto pessoaFisica;
//	PessoaJuridicaDto pessoaJuridica;
//	EnderecoDto endereco;
	String email;
	String senha;
	Integer idTipoUsuario;
}
