package br.edu.ifsp.spo.bike_integration.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "trecho")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trecho {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "latitude_inicial")
	private Double latitudeInicial;
	
	@Column(name = "longitude_inicial")
	private Double longitudeInicial;
	
	@Column(name = "latitude_final")
	private Double latitudeFinal;
	
	@Column(name = "longitude_final")
	private Double longitudeFinal;
	
	@Column(name = "cep")
	private String cep;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_infraestrutura_cicloviaria", nullable = false)
	private InfraestruturaCicloviaria infraestruturasCicloviarias	;

}
