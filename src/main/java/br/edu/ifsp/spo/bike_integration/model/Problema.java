package br.edu.ifsp.spo.bike_integration.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    private Integer id;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "criado_em", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime criadoEm;

    @Column(nullable = false)
    private Boolean valido;

    @Column(nullable = false)
    private Boolean ativo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_tipo_problema", nullable = false)
    private TipoProblema tipoProblema;
    
    @ManyToMany
    @JoinTable(
        name = "problema_infraestrutura_cicloviaria",
        joinColumns = @JoinColumn(name = "problema_id"),
        inverseJoinColumns = @JoinColumn(name = "infraestrutura_cicloviaria_id")
    )
    private List<InfraestruturaCicloviaria> infraestruturasCicloviarias;
}
