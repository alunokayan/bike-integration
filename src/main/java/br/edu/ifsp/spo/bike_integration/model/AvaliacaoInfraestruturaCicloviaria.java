package br.edu.ifsp.spo.bike_integration.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "avaliacao_infraestrutura_cicloviaria")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoInfraestruturaCicloviaria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_infraestrutura_cicloviaria")
	private InfraestruturaCicloviaria infraestruturaCicloviaria;

	@Column(name = "nota")
	private Integer nota;

	@Column(name = "comentario")
	private String comentario;

	@Column(name = "dt_criacao")
	private LocalDateTime dtCriacao;

}