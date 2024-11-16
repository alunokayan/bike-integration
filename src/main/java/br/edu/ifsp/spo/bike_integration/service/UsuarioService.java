package br.edu.ifsp.spo.bike_integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.dto.UsuarioDto;
import br.edu.ifsp.spo.bike_integration.factory.UsuarioFactory;
import br.edu.ifsp.spo.bike_integration.model.Usuario;
import br.edu.ifsp.spo.bike_integration.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaFisicaService pessoaFisicaService;

    @Autowired
    private PessoaJuridicaService pessoaJuridicaService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioFactory usuarioFactory;

	public Usuario getByNomeUsuario(String nomeUsuario) {
    return usuarioRepository.findByNomeUsuario(nomeUsuario)
        .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }
    
    public Usuario create(UsuarioDto usuarioDto) throws Exception {
    	if (usuarioDto.getCnpj() != null && usuarioDto.getCpf() != null)
    		throw new IllegalArgumentException("Informe CPF ou CNPJ, não ambos");
    	
    	if (usuarioDto.getCnpj() == null && usuarioDto.getCpf() == null)
    		throw new IllegalArgumentException("Informe CPF ou CNPJ");
    	
        if (usuarioRepository.findByNomeUsuario(usuarioDto.getNomeUsuario()).isPresent())
            throw new IllegalArgumentException("Nome de usuário já existe");

        if (emailService.getEmailByEndereco(usuarioDto.getEmail()) != null)
            throw new IllegalArgumentException("Email já cadastrado");

        if (pessoaFisicaService.getPessoaFisicaByCpf(usuarioDto.getCpf()) != null)
            throw new IllegalArgumentException("CPF já cadastrado");

        if (pessoaJuridicaService.getPessoaJuridicaByCnpj(usuarioDto.getCnpj()) != null)
            throw new IllegalArgumentException("CNPJ já cadastrado");

        return usuarioRepository.save(usuarioFactory.fromDto(usuarioDto));
    }
    
	public void delete(String nomeUsuario, String cpf, String cnpj) {
		Usuario usuario = usuarioRepository.findByNomeUsuario(nomeUsuario)
				.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
		
		if (cpf == null && cnpj == null)
			throw new IllegalArgumentException("Informe CPF ou CNPJ para deletar o usuário");
		
		if (cpf != null && !usuario.getPessoaFisica().getCpf().equals(cpf))
                throw new IllegalArgumentException("CPF não corresponde ao usuário");
		
		if (cnpj != null && !usuario.getPessoaJuridica().getCnpj().equals(cnpj))
				throw new IllegalArgumentException("CNPJ não corresponde ao usuário");
		
		usuarioRepository.delete(usuario);
	}
}
