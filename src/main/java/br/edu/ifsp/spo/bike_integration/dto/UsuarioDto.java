package br.edu.ifsp.spo.bike_integration.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

	@Schema(example = "Usuário Padrão")
	private String nome;

	@Schema(example = "usuario123")
	private String nomeUsuario;

	@Schema(example = "1")
	private int idPerfil;

	@Schema(example = "000.000.000-00")
	private String cpf;

	@Schema(example = "00.000.000/0001-00")
	private String cnpj;

	private EnderecoDto endereco;

	@Schema(example = "email@exemplo.com")
	private String email;

	@Schema(example = "senha123")
	private String senha;

	@Schema(example = "2")
	private int idTipoUsuario;

}
