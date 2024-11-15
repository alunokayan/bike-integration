
package br.edu.ifsp.spo.bike_integration.util;

public class FormatUtil {

    public static String formatCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            return cpf;
        }
        cpf = cpf.replaceAll("\\D", ""); // Remove tudo que não for número
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public static String formatCnpj(String cnpj) {
        if (cnpj == null || cnpj.isEmpty()) {
            return cnpj;
        }
        cnpj = cnpj.replaceAll("\\D", ""); // Remove tudo que não for número
        return cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
    }
    
	public static String formatCep(String cep) {
		if (cep == null || cep.isEmpty()) {
			return cep;
		}
		cep = cep.replaceAll("\\D", ""); // Remove tudo que não for número
        return cep.replaceAll("(\\d{5})(\\d{3})", "$1-$2");
	}
}