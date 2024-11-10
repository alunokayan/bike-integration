package br.edu.ifsp.spo.bike_integration.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "infraestrutura_cicloviaria")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfraestruturaCicloviaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "criado_em", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime criadoEm;

    @Column(name = "nota_media", nullable = false)
    private Integer notaMedia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_tipo_infraestrutura_cicloviaria", nullable = false)
    private TipoInfraestruturaCicloviaria tipoInfraestruturaCicloviaria;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_localizacao", nullable = false)
    private Localizacao localizacao;
    
    @ManyToMany(mappedBy = "infraestruturasCicloviarias")
    private List<Problema> problemas;
}
