package br.edu.ifsp.spo.bike_integration.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "localizacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude", nullable = false)
    private String latitude;

    @Column(name = "longitude", nullable = false)
    private String longitude;

    @Column(name = "cep", nullable = false)
    private String cep;

    @Column(name = "criado_em", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date criadoEm;

    @Column(name = "atualizado_em")
    private Date atualizadoEm;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_tipo_localizacao", nullable = false)
    private TipoLocalizacao tipoLocalizacao;
    
    @PrePersist
    public void prePersist() {
    this.criadoEm = new Date();
    }
}
