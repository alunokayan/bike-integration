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
@Table(name = "tipo_evento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoEvento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "faixa_km")
	private String faixaKm;
	
	@Column(name = "gratuito")
	private boolean gratuito;
	
	@ManyToOne
	@JoinColumn(name = "id_nivel_habilidade")
	private NivelHabilidade nivelHabilidade;
	
}
