
package br.edu.ifsp.spo.bike_integration.util;

import br.edu.ifsp.spo.bike_integration.dto.EnderecoDto;
import br.edu.ifsp.spo.bike_integration.response.BrasilApiCepResponse;

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

	public static String removeNonNumeric(String str) {
		return str.replaceAll("\\D", "");
	}

	public static String formatEnderecoToOpenStreetMapApi(EnderecoDto endereco) {
		return endereco.getRua() + ", " + endereco.getNumero() + " - " + endereco.getBairro() + ", "
				+ endereco.getCidade() + " - " + endereco.getEstado();
	}

	public static EnderecoDto convertToDto(BrasilApiCepResponse endereco, Long numero) {
		return EnderecoDto.builder().cep(endereco.getCep()).estado(endereco.getState()).cidade(endereco.getCity())
				.bairro(endereco.getNeighborhood()).rua(endereco.getStreet()).numero(numero).build();
	}
}