package br.com.infnet.registroguilda.aventura.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "participacoes_missao", schema = "aventura", uniqueConstraints = {
                @UniqueConstraint(name = "uk_missao_aventureiro", columnNames = {"missao_id", "aventureiro_id"})
        }
)
public class ParticipacaoMissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "missao_id", nullable = false)
    private Missao missao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Aventureiro aventureiro;

    @Column(name = "papel_na_missao", nullable = false)
    private String papelNaMissao;

    @Column(name = "recompensa_ouro", precision = 10, scale = 2)
    private BigDecimal recompensaOuro;

    @Column(name = "destaque", nullable = false)
    private Boolean destaque;

    @Column(name = "data_registro", nullable = false)
    private OffsetDateTime dataRegistro;
}