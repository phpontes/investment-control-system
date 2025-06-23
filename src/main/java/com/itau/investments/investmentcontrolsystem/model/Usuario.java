package com.itau.investments.investmentcontrolsystem.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(name = "perc_corretagem", nullable = false, precision = 5, scale = 2)
	private BigDecimal percCorretagem;
}
