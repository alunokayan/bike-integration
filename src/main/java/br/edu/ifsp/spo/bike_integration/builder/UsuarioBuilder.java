package br.edu.ifsp.spo.bike_integration.builder;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifsp.spo.bike_integration.dto.EnderecoDto;
import br.edu.ifsp.spo.bike_integration.model.Email;
import br.edu.ifsp.spo.bike_integration.model.PessoaFisica;
import br.edu.ifsp.spo.bike_integration.model.PessoaJuridica;
import br.edu.ifsp.spo.bike_integration.model.Senha;
import br.edu.ifsp.spo.bike_integration.model.Usuario;
import br.edu.ifsp.spo.bike_integration.service.PerfilService;
import br.edu.ifsp.spo.bike_integration.service.TipoUsuarioService;
import br.edu.ifsp.spo.bike_integration.util.CryptoUtil;
import br.edu.ifsp.spo.bike_integration.util.FormatUtil;

@Component
public class UsuarioBuilder {
	
	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private TipoUsuarioService tipoUsuarioService;
	
	@Autowired
	private EnderecoBuilder enderecoBuilder;

	public Usuario build(String nome, String nomeUsuario, Long idPerfil, String cpf, String cnpj, EnderecoDto endereco,
			String email, String senha, Long idTipoUsuario) throws Exception {		
	// Gera chave de criptografia
	SecretKey key = CryptoUtil.generateKey();
	
	// Constrói pessoa física e jurídica
	PessoaFisica pessoaFisica = this.buildPessoaFisica(nomeUsuario, cpf);
	PessoaJuridica pessoaJuridica = this.buildPessoaJuridica(nome, cnpj);
	
	return Usuario.builder()
			.nomeUsuario(nomeUsuario)
			.perfil(perfilService.findById(idPerfil))
			.pessoaFisica(pessoaFisica)
			.pessoaJuridica(pessoaJuridica)
			.endereco(enderecoBuilder.build(endereco))
			.email(Email.builder()
                    .valor(email)
                    .validado(true)
                    .build())
			.senha(Senha.builder()
                    .valor(CryptoUtil.encrypt(senha, key))
                    .chave(CryptoUtil.getSecretKeyAsString(key))
                    .build())
			.tipoUsuario(tipoUsuarioService.getTipoUsuarioById(idTipoUsuario))
			.build();
	}
	
	// PRIVATES
	private PessoaFisica buildPessoaFisica(String nome, String cpf) {
	return cpf != null ? PessoaFisica.builder().nome(nome).cpf(FormatUtil.formatCpf(cpf)).build() : null;
	}
	
	private PessoaJuridica buildPessoaJuridica(String nome, String cnpj) {
	return cnpj != null ? PessoaJuridica.builder().nome(nome).cnpj(FormatUtil.formatCnpj(cnpj)).build() : null;
	}
	
	
	
}
