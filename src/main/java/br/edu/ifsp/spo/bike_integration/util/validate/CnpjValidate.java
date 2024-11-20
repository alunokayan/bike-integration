package br.edu.ifsp.spo.bike_integration.util.validate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifsp.spo.bike_integration.service.BrasilApiService;

@Component
public class CnpjValidate {

	@Autowired
	private BrasilApiService brasilApiService;
	
	public List<String> validate(String cnpj, List<String> exceptions) {
		validateCnpjLength(cnpj, exceptions);
		validateCnpjNumeric(cnpj, exceptions);
		validateCnpjValidity(cnpj, exceptions);
		return exceptions;
	}
	
	// PRIVATE METHODS
	private void validateCnpjLength(String cnpj, List<String> exceptions) {
		if (cnpj.length() != 14)
			exceptions.add("CNPJ deve conter 14 caracteres");
	}
	
	private void validateCnpjNumeric(String cnpj, List<String> exceptions) {
		if (!cnpj.matches("[0-9]+"))
			exceptions.add("CNPJ deve conter apenas números");
	}
	
	private void validateCnpjValidity(String cnpj, List<String> exceptions) {
		if (!isCnpjValid(cnpj))
			exceptions.add("CNPJ inválido ou inativo");
	}

	private boolean isCnpjValid(String cnpj) {
		try {
			return "Ativa".equals(brasilApiService.buscarEmpresaPorCnpj(cnpj).getDescricaoSituacaoCadastral());
		} catch (Exception e) {
			return false;
		}
		
	}
	
}
