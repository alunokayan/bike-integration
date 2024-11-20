package br.edu.ifsp.spo.bike_integration.util.validate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifsp.spo.bike_integration.dto.UsuarioDto;
import br.edu.ifsp.spo.bike_integration.repository.UsuarioRepository;
import br.edu.ifsp.spo.bike_integration.service.EmailService;
import br.edu.ifsp.spo.bike_integration.service.PessoaFisicaService;
import br.edu.ifsp.spo.bike_integration.service.PessoaJuridicaService;

@Component
public class UsuarioCreateValidate {
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	@Autowired
    private PessoaFisicaService pessoaFisicaService;

    @Autowired
    private PessoaJuridicaService pessoaJuridicaService;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private CpfValidate cpfValidate;
    
    @Autowired
    private CnpjValidate cnpjValidate;

    public void validate(UsuarioDto usuarioDto) {
        List<String> exceptions = new ArrayList<>();
        
        boolean hasCpf = usuarioDto.getCpf() != null;
        boolean hasCnpj = usuarioDto.getCnpj() != null;
        
        if (hasCpf)
        	 exceptions.addAll(cpfValidate.validate(usuarioDto.getOnlyNumbersCpf(), new ArrayList<>()));
        
        if (hasCnpj)
            exceptions.addAll(cnpjValidate.validate(usuarioDto.getOnlyNumbersCnpj(), new ArrayList<>()));

        if (hasCpf && hasCnpj)
            exceptions.add("Informe CPF ou CNPJ, não ambos");

        if (!hasCpf && !hasCnpj)
            exceptions.add("Informe CPF ou CNPJ");

        if (usuarioRepository.findByNomeUsuario(usuarioDto.getNomeUsuario()).isPresent())
            exceptions.add("Nome de usuário já existe");

        if (emailService.getEmailByEndereco(usuarioDto.getEmail()) != null)
            exceptions.add("Email já cadastrado");

        if (pessoaFisicaService.getPessoaFisicaByCpf(usuarioDto.getCpf()) != null)
            exceptions.add("CPF já cadastrado");

        if (pessoaJuridicaService.getPessoaJuridicaByCnpj(usuarioDto.getCnpj()) != null)
            exceptions.add("CNPJ já cadastrado");

        if (!exceptions.isEmpty())
        	throw new IllegalArgumentException(String.join("\n", exceptions));
    }

}