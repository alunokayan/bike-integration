package br.edu.ifsp.spo.bike_integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.dto.UsuarioDto;
import br.edu.ifsp.spo.bike_integration.factory.UsuarioFactory;
import br.edu.ifsp.spo.bike_integration.model.Usuario;
import br.edu.ifsp.spo.bike_integration.repository.UsuarioRepository;
import br.edu.ifsp.spo.bike_integration.util.validate.UsuarioCreateValidate;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioFactory usuarioFactory;
    
    @Autowired
    private UsuarioCreateValidate usuarioCreateValidate;

	public Usuario getByNomeUsuario(String nomeUsuario) {
		return this.getUsuarioByName(nomeUsuario);
    }
    
    public Usuario create(UsuarioDto usuarioDto) throws Exception {
    	usuarioCreateValidate.validate(usuarioDto);
        return usuarioRepository.save(usuarioFactory.fromDto(usuarioDto));
    }
    
	public void delete(String nomeUsuario, String cpf, String cnpj) {
		Usuario usuario = this.getUsuarioByName(nomeUsuario);
		
		if (cpf == null && cnpj == null)
			throw new IllegalArgumentException("Informe CPF ou CNPJ para deletar o usuário");
		
		if (cpf != null && !usuario.getPessoaFisica().getCpf().equals(cpf))
                throw new IllegalArgumentException("CPF não corresponde ao usuário");
		
		if (cnpj != null && !usuario.getPessoaJuridica().getCnpj().equals(cnpj))
				throw new IllegalArgumentException("CNPJ não corresponde ao usuário");
		
		usuarioRepository.delete(usuario);
	}
	
	// PRIVATE METHODS
	private Usuario getUsuarioByName(String nomeUsuario) {
		return usuarioRepository.findByNomeUsuario(nomeUsuario)
		        .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
	}
}
