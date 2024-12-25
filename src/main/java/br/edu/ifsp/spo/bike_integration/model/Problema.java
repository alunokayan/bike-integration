package br.edu.ifsp.spo.bike_integration.model;

import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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

    @Column(name = "dt_criacao", nullable = false, updatable = false)
    private Date dtCriacao;

    @Column(name = "validado", nullable = false)
    private Boolean validado;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @PrePersist
    public void prePersist() {
        this.dtCriacao = new Timestamp(System.currentTimeMillis());
    }
}