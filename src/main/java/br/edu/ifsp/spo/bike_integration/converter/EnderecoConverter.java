package br.edu.ifsp.spo.bike_integration.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ifsp.spo.bike_integration.dto.EnderecoDto;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EnderecoConverter implements AttributeConverter<EnderecoDto, String> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(EnderecoDto endereco) {
		try {
			return objectMapper.writeValueAsString(endereco);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Erro ao converter Endereco para JSON", e);
		}
	}

	@Override
	public EnderecoDto convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, EnderecoDto.class);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Erro ao converter JSON para Endereco", e);
		}
	}
}
