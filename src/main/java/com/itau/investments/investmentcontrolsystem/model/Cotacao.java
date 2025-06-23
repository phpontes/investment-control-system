package com.itau.investments.investmentcontrolsystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cotacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cotacao {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ativo_id")
    private Ativo ativo;

    @Column(name = "preco_unitario", nullable = false, precision = 18, scale = 8)
    private BigDecimal precoUnitario;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;
}
