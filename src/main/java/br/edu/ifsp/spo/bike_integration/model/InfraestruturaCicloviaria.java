package br.edu.ifsp.spo.bike_integration.model;

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
@Table(name = "infraestrutura_cicloviaria")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfraestruturaCicloviaria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "nota_media")
	private Double notaMedia;
	
	@Column(name = "latitude_inicial")
	private Double latitudeInicial;
	
	@Column(name = "longitude_inicial")
	private Double longitudeInicial;
	
	@Column(name = "latitude_final")
	private Double latitudeFinal;
	
	@Column(name = "longitude_final")
	private Double longitudeFinal;
	
	@ManyToOne
	@JoinColumn(name = "tipo_infraestrutura_cicloviaria_id")
	private TipoInfraestruturaCicloviaria tipoInfraestruturaCicloviariaId;
	
}
