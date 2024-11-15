package br.edu.ifsp.spo.bike_integration.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "configuracao_api_externa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracaoApiExterna {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome", nullable = false, unique = true)
	private String nome;

	@Column(name = "url", nullable = false)
	private String url;

	@Column(name = "chave", nullable = true)
	private String chave;

	@Column(name = "criado_em", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date criadoEm;

	@Column(name = "atualizado_em")
	private Date atualizadoEm;

	@PrePersist
	public void prePersist() {
	this.criadoEm = new Date();
	}
}
