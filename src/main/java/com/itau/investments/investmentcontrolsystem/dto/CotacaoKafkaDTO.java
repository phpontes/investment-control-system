package com.itau.investments.investmentcontrolsystem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CotacaoKafkaDTO {
    private Long ativoId;
    private BigDecimal precoUnitario;
    private LocalDateTime dataHora;
}
