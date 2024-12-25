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

	@Column(name = "email", nullable = false)
	private String email;

	@PrePersist
	public void prePersist() {
		this.dtCriacao = new Date();
	}
}
