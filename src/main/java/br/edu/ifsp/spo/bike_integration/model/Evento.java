package br.edu.ifsp.spo.bike_integration.model;

import java.util.Date;

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
@Table(name = "evento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "criado_em", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date criadoEm;

    @Column(name = "atualizado_em")
    private Date atualizadoEm;

    @Column(name = "data_inicial", nullable = false)
    private Date dataInicial;

    @Column(name = "data_final", nullable = false)
    private Date dataFinal;

    @Column(name = "gratuito", nullable = false)
    private Boolean gratuito;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_tipo_evento", nullable = false)
    private TipoEvento tipoEvento;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    
    @PrePersist
	public void prePersist() {
	this.criadoEm = new Date();
	}
}
