package br.edu.ifsp.spo.bike_integration.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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

    @Column(name = "nome_usuario", nullable = false, unique = true)
    private String nomeUsuario;

    @Column(name = "criado_em", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime criadoEm;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pessoa_fisica")
    private PessoaFisica pessoaFisica;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pessoa_juridica")
    private PessoaJuridica pessoaJuridica;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_tipo_usuario", nullable = false)
    private TipoUsuario tipoUsuario;
    
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_email")
    private Email email;
    
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_senha")
    private Senha senha;
    
    @PrePersist
	public void prePersist() {
		this.criadoEm = LocalDateTime.now();
	}
}
