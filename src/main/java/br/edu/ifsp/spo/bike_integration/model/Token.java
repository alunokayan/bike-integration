package br.edu.ifsp.spo.bike_integration.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "valor", nullable = false)
	private String tokenGerado;
	
	@Column(name = "dt_criacao", nullable = false, updatable = false)
	private Date dtCriacao;
	
	@Column(name = "dt_expiracao", nullable = false)
	private Date dtExpiracao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;
	
	@PrePersist
	public void prePersist() {
		this.dtCriacao = new Date();
	}
}
