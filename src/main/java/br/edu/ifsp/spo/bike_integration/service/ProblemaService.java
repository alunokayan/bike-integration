package br.edu.ifsp.spo.bike_integration.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifsp.spo.bike_integration.aws.service.S3Service;
import br.edu.ifsp.spo.bike_integration.dto.ProblemaDTO;
import br.edu.ifsp.spo.bike_integration.model.Problema;
import br.edu.ifsp.spo.bike_integration.model.Trecho;
import br.edu.ifsp.spo.bike_integration.repository.ProblemaRepository;
import br.edu.ifsp.spo.bike_integration.util.S3Utils;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service
public class ProblemaService {

	@Autowired
	private ProblemaRepository problemaRepository;

	@Autowired
	private TrechoService trechoService;

	@Autowired
	private TipoProblemaService tipoProblemaService;

	@Autowired
	private S3Service s3Service;

	@Value("${aws.s3.bucket-name}")
	private String bucketName;

	public Problema loadProblemaById(Long id) {
		return problemaRepository.findById(id).orElse(null);
	}

	public Problema create(ProblemaDTO problema) {
		Trecho trecho = trechoService.findTrechoProximoByLocation(problema.getLatitude(), problema.getLongitude());
		if (problemaRepository.existsByTrechoAndTipoProblema(trecho,
				tipoProblemaService.loadTipoProblema(problema.getIdTipoProblema()))) {
			throw new RuntimeException(
					"Já existe um problema cadastrado nesse trecho com esse tipo de problema.");
		}

		return problemaRepository.save(Problema.builder()
				.descricao(problema.getDescricao())
				.validado(false)
				.ativo(true)
				.trecho(trecho)
				.tipoProblema(tipoProblemaService.loadTipoProblema(problema.getIdTipoProblema()))
				.build());
	}

	public Problema updateProblema(Long id, ProblemaDTO problema) {
		Problema problemaAtual = loadProblemaById(id);
		if (problemaAtual != null) {
			problemaAtual.setDescricao(
					problema.getDescricao() != null ? problema.getDescricao() : problemaAtual.getDescricao());
			problemaAtual
					.setValidado(problema.getValidado() != null ? problema.getValidado() : problemaAtual.getValidado());
			problemaAtual.setAtivo(problema.getAtivo() != null ? problema.getAtivo() : problemaAtual.getAtivo());
			problemaAtual.setTrecho(
					trechoService.findTrechoProximoByLocation(problema.getLatitude(), problema.getLongitude()));
			problemaAtual.setTipoProblema(tipoProblemaService.loadTipoProblema(problema.getIdTipoProblema()));
			return problemaRepository.save(problemaAtual);
		}
		return null;
	}

	public void updateFotoProblema(Long idProblema, MultipartFile file) {
		try {
			Problema problema = loadProblemaById(idProblema);
			if (problema != null) {
				String s3Key = S3Utils.createS3Key("problema", problema.getId(), file);
				PutObjectResponse response = s3Service.put(PutObjectRequest.builder()
						.bucket(bucketName)
						.key(s3Key)
						.contentType(file.getContentType())
						.build(), file.getBytes());
				if (response.sdkHttpResponse().isSuccessful()) {
					problema.setS3Url(s3Service.getUrl(s3Key));
					problemaRepository.save(problema);
				} else {
					throw new RuntimeException("Erro ao salvar a foto do problema.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delete(Long id) {
		Problema problema = loadProblemaById(id);
		if (problema == null) {
			throw new RuntimeException("Problema não encontrado.");
		}
		problemaRepository.delete(problema);
	}

	public List<Problema> buscarProblemasByRadius(Double latitude, Double longitude, Double raio) {
		return problemaRepository.findProblemasProximosByLocation(latitude, longitude, raio);
	}

}
