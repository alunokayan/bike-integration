package br.edu.ifsp.spo.bike_integration.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifsp.spo.bike_integration.dto.EnderecoDto;
import br.edu.ifsp.spo.bike_integration.model.Endereco;
import br.edu.ifsp.spo.bike_integration.response.BrasilApiCepResponse;
import br.edu.ifsp.spo.bike_integration.service.BrasilApiService;

@Component
public class EnderecoBuilder {

    @Autowired
    private BrasilApiService brasilApi;

    public Endereco build(EnderecoDto endereco) throws Exception {
        if (endereco.hasSecondAtributes()) {
            return buildFromAttributes(
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getLatitude(),
                endereco.getLongitude()
            );
        } else {
            return buildFromApi(endereco.getCep(), endereco.getNumero(), endereco.getComplemento());
        }
    }

    
    // PRIVATES
	private Endereco buildFromAttributes(String rua, Long numero, String complemento, String bairro, String cidade,
			String estado, String cep, String latitude, String longitude) {
		return Endereco.builder().numero(numero).complemento(complemento).rua(rua).bairro(bairro).cidade(cidade)
				.estado(estado).cep(cep).latitude(latitude).longitude(longitude).build();
	}
    
	private Endereco buildFromApi(String cep, Long numero, String complemento) throws Exception {
		BrasilApiCepResponse enderecoFromApi = brasilApi.buscarEnderecoPorCep(cep);

        return Endereco.builder()
            .numero(numero)
            .complemento(complemento)
            .rua(enderecoFromApi.getStreet())
            .bairro(enderecoFromApi.getNeighborhood())
            .cidade(enderecoFromApi.getCity())
            .estado(enderecoFromApi.getState())
            .cep(enderecoFromApi.getCep())
            .latitude(enderecoFromApi.getLocation().getCoordinates().getLatitude())
            .longitude(enderecoFromApi.getLocation().getCoordinates().getLongitude())
            .build();
    }
}
