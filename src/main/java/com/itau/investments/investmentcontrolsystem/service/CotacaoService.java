package com.itau.investments.investmentcontrolsystem.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itau.investments.investmentcontrolsystem.model.*;
import com.itau.investments.investmentcontrolsystem.repository.*;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CotacaoService {

	private final CotacaoRepository cotacaoRepository;
	private final AtivoRepository ativoRepository;
	
	public Cotacao registrarCotacao(Long ativoId, BigDecimal precoUnitario) {
		Ativo ativo = ativoRepository.findById(ativoId)
				.orElseThrow(() -> new EntityNotFoundException("Ativo não encontrado"));
		
		Cotacao cotacao = Cotacao.builder()
				.ativo(ativo)
				.precoUnitario(precoUnitario)
				.dataHora(LocalDateTime.now())
				.build();
		
		return cotacaoRepository.save(cotacao);
	}
	
	public Cotacao buscarUltimaCotacao(Long ativoId) {
		Ativo ativo = ativoRepository.findById(ativoId)
				.orElseThrow(() -> new EntityNotFoundException("Ativo não encontrado."));
		
		Cotacao cotacao = cotacaoRepository.findTop1ByAtivoOrderByDataHoraDesc(ativo);
		if (cotacao == null) {
			throw new EntityNotFoundException("Nenhuma cotação encontrada para o ativo.");
		}
		return cotacao;
	}
	
	public List<Cotacao> listarHistoricoPorAtivo(Long ativoId) {
		Ativo ativo = ativoRepository.findById(ativoId)
				.orElseThrow(() -> new EntityNotFoundException("Ativo não encontrado."));
		return cotacaoRepository.findByAtivoOrderByDataHoraDesc(ativo);
	}
}
