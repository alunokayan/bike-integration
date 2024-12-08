package br.edu.ifsp.spo.bike_integration.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.edu.ifsp.spo.bike_integration.converter.EnderecoConverter;
import br.edu.ifsp.spo.bike_integration.dto.EnderecoDto;
import br.edu.ifsp.spo.bike_integration.exception.CryptoException;
import br.edu.ifsp.spo.bike_integration.util.CryptoUtil;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "nome_usuario", nullable = false, unique = true)
	private String nomeUsuario;

	@Column(name = "endereco", nullable = false)
	@Convert(converter = EnderecoConverter.class)
	private EnderecoDto endereco;

	@Column(name = "e-mail", nullable = false)
	private String email;

	@Column(name = "senha", nullable = false)
	private String senha;

	@Column(name = "hash", nullable = false)
	private String hash;

	@Column(name = "cpf", nullable = true)
	private String cpf;

	@Column(name = "cnpj", nullable = true)
	private String cnpj;

	@Column(name = "dt_criacao", nullable = false)
	private Date dtCriacao;

	@ManyToOne
	@JoinColumn(name = "id_nivel_habilidade", nullable = false)
	private NivelHabilidade nivelHabilidade;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Token> tokens;

	@Transient
	private Token lastToken;

	public Token getLastToken() {
		if (this.tokens != null && !this.tokens.isEmpty()) {
			this.lastToken = this.tokens.stream().max((t1, t2) -> t1.getDtCriacao().compareTo(t2.getDtCriacao()))
					.orElse(null);
		}
		return this.lastToken;
	}

	@PrePersist
	public void prePersist() throws CryptoException {
		this.dtCriacao = new Date();
		this.hash = CryptoUtil.generateKeyAsString();
		this.senha = CryptoUtil.encrypt(this.senha, this.hash);
	}
}
