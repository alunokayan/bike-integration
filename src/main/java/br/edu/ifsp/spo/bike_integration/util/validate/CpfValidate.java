package br.edu.ifsp.spo.bike_integration.util.validate;

import org.springframework.stereotype.Component;

import br.com.caelum.stella.validation.CPFValidator;
import br.edu.ifsp.spo.bike_integration.util.FormatUtil;

@Component
public interface CpfValidate {

	String CPF_LENGTH_ERROR = "CPF deve conter 11 caracteres";
	String CPF_INVALID_ERROR = "CPF inválido";
	String CPF_VALID = "CPF válido";

	static String validate(String cpf) {
		cpf = FormatUtil.removeNonNumeric(cpf);

		if (!isCpfLengthValid(cpf)) {
			return CPF_LENGTH_ERROR;
		}
		if (!isCpfValid(cpf)) {
			return CPF_INVALID_ERROR;
		}
		return CPF_VALID;
	}

	// PRIVATE METHODS
	private static boolean isCpfLengthValid(String cpf) {
		return cpf.length() == 11;
	}

	private static boolean isCpfValid(String cpf) {
		CPFValidator cpfValidator = new CPFValidator();
		return cpfValidator.invalidMessagesFor(cpf).isEmpty();
	}
}
