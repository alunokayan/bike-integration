package br.edu.ifsp.spo.bike_integration.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "problema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Problema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false)
    private String descricao;
    
    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "criado_em", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date criadoEm;

    @Column(nullable = false)
    private Boolean ativo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_tipo_problema", nullable = false)
    private TipoProblema tipoProblema;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_infraestrutura_cicloviaria", nullable = false)
    private InfraestruturaCicloviaria infraestruturaCicloviaria;
    
    @PrePersist
	public void prePersist() {
	this.criadoEm = new Date();
	}
}
