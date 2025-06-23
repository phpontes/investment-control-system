package com.itau.investments.investmentcontrolsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "posicoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Posicao {

    @EmbeddedId
    private PosicaoId id;

    @ManyToOne(optional = false)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @MapsId("ativoId")
    @JoinColumn(name = "ativo_id")
    private Ativo ativo;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "preco_medio", nullable = false, precision = 18, scale = 8)
    private BigDecimal precoMedio;

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal pnl;
}
