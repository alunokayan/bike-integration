package br.edu.ifsp.spo.bike_integration.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private String latitudeInicial;
	
	@Column(name = "longitude_inicial")
	private String longitudeInicial;
	
	@Column(name = "latitude_final")
	private String latitudeFinal;
	
	@Column(name = "longitude_final")
	private String longitudeFinal;
	
	@Column(name = "cep")
	private String cep;

}
