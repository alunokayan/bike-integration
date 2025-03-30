package br.edu.ifsp.spo.bike_integration.model;

import java.time.LocalDateTime;

import br.edu.ifsp.spo.bike_integration.converter.EnderecoConverter;
import br.edu.ifsp.spo.bike_integration.dto.EnderecoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "evento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "data")
	private LocalDateTime data;

	@Column(name = "dt_atualizacao")
	private LocalDateTime dtAtualizacao;

	@Column(name = "endereco", nullable = false)
	@Convert(converter = EnderecoConverter.class)
	private EnderecoDTO endereco;

	@Column(name = "faixa_km")
	private Long faixaKm;

	@Column(name = "gratuito")
	private boolean gratuito;

	@ManyToOne
	@JoinColumn(name = "id_tipo_evento")
	private TipoEvento tipoEvento;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@Column(name = "aprovado", nullable = false)
	private boolean aprovado;

	@Column(name = "s3_key")
	private String s3Key;

	@PrePersist
	void prePersist() {
		this.dtAtualizacao = LocalDateTime.now();
	}

}
