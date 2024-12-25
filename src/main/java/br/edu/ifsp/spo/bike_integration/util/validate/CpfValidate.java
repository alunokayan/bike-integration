
package br.edu.ifsp.spo.bike_integration.util.validate;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.caelum.stella.validation.CPFValidator;

@Component
public class CpfValidate {

    public List<String> validate(String cpf, List<String> exceptions) {
        validateCpfLength(cpf, exceptions);
        validateCpfNumeric(cpf, exceptions);
        validateCpfValidity(cpf, exceptions);
        return exceptions;
    }

    // PRIVATE METHODS
    private void validateCpfLength(String cpf, List<String> exceptions) {
        if (cpf.length() != 11)
            exceptions.add("CPF deve conter 11 caracteres");
    }

    private void validateCpfNumeric(String cpf, List<String> exceptions) {
        if (!cpf.matches("[0-9]+"))
            exceptions.add("CPF deve conter apenas números");
    }

    private void validateCpfValidity(String cpf, List<String> exceptions) {
        if (!isCpfValid(cpf))
            exceptions.add("CPF inválido");
    }

    private boolean isCpfValid(String cpf) {
    	CPFValidator cpfValidator = new CPFValidator();
    	return cpfValidator.invalidMessagesFor(cpf).isEmpty();
    }
}
