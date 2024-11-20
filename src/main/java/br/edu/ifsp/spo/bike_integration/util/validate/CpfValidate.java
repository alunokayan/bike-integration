
package br.edu.ifsp.spo.bike_integration.util.validate;

import java.util.List;

import org.springframework.stereotype.Component;

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
        int[] numbers = new int[11];
        for (int i = 0; i < 11; i++) {
            numbers[i] = Integer.parseInt(cpf.substring(i, i + 1));
        }
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += numbers[i] * (10 - i);
        }
        int remainder = sum % 11;
        if (remainder == 1 || remainder == 0) {
            if (numbers[9] != 0) {
                return false;
            }
        } else {
            if (numbers[9] != 11 - remainder) {
                return false;
            }
        }
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += numbers[i] * (11 - i);
        }
        remainder = sum % 11;
        if (remainder == 1 || remainder == 0) {
            if (numbers[10] != 0) {
                return false;
            }
        } else {
            if (numbers[10] != 11 - remainder) {
                return false;
            }
        }
        return true;
    }
}
