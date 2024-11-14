package br.edu.ifsp.spo.bike_integration.model;

import java.util.Date;

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
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "infraestrutura_cicloviaria_filho")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfraestruturaCicloviariaFilho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_infraestrutura_pai", nullable = false)
    private InfraestruturaCicloviaria infraestruturaCicloviaria;
    
    @OneToOne(optional = false)
    @JoinColumn(name = "id_localizacao", nullable = false)
    private Localizacao localizacao;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_tipo_infraestrutura_cicloviaria", nullable = false)
    private TipoInfraestruturaCicloviaria tipoInfraestruturaCicloviaria;
    
    @Column(name = "criado_em", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date criadoEm;
    
    @PrePersist
    public void prePersist() {
    criadoEm = new Date();
    }
}
