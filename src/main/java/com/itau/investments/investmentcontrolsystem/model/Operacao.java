package com.itau.investments.investmentcontrolsystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.itau.investments.investmentcontrolsystem.enums.TipoOperacao;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "operacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ativo_id")
	private Ativo ativo;
	
	@Column(nullable = false)
	private Integer quantidade;
	
	@Column(name = "preco_unitario", nullable = false, precision = 18, scale = 8)
	private BigDecimal precoUnitario;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_operacao", nullable = false, length = 10)
	private TipoOperacao tipoOperacao;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal corretagem;
	
	@Column(name = "data_hora", nullable = false)
	private LocalDateTime dataHora;
}
