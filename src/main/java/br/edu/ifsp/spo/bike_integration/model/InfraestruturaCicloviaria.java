package br.edu.ifsp.spo.bike_integration.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "infraestrutura_cicloviaria")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configurable
public class InfraestruturaCicloviaria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "json_id")
	private String jsonId;

	@Column(name = "nome_localidade")
	private String nome;

	@Column(name = "nota_media")
	private Double notaMedia;

	@Column(name = "geometria")
	private String geometria;

	@ManyToOne
	@JoinColumn(name = "id_tipo_infraestrutura_cicloviaria")
	private TipoInfraestruturaCicloviaria tipoInfraestruturaCicloviariaId;

	@OneToMany(mappedBy = "infraestruturaCicloviaria", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Trecho> trechos;
}
