package com.itau.investments.investmentcontrolsystem.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itau.investments.investmentcontrolsystem.enums.TipoOperacao;
import com.itau.investments.investmentcontrolsystem.model.*;
import com.itau.investments.investmentcontrolsystem.repository.*;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PosicaoService {

	private final PosicaoRepository posicaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AtivoRepository ativoRepository;
    private final OperacaoRepository operacaoRepository;
    
    public List<Posicao> listarPosicoesPorUsuario(Long usuarioId) {
    	Usuario usuario = usuarioRepository.findById(usuarioId)
    			.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    	return posicaoRepository.findByUsuario(usuario);
    }
    
    public Posicao buscarPosicaoPorUsuarioEAtivo(Long usuarioId, Long ativoId) {
    	Usuario usuario = usuarioRepository.findById(usuarioId)
    			.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    	Ativo ativo = ativoRepository.findById(ativoId)
    			.orElseThrow(() -> new EntityNotFoundException("Ativo não encontrado."));
    	
    	return posicaoRepository.findByUsuarioAndAtivo(usuario, ativo)
    			.orElseThrow(() -> new EntityNotFoundException("Posição não encontrada para este usuário e ativo."));
    }
	
    public BigDecimal calcularTotalInvestidoPorAtivo(Long usuarioId, Long ativoId) {
    	return operacaoRepository.findByUsuarioIdAndAtivoIdAndTipoOperacaoOrderByDataHoraAsc(
    					usuarioId, ativoId, TipoOperacao.COMPRA)
    			.stream()
    			.map(op -> op.getPrecoUnitario().multiply(BigDecimal.valueOf(op.getQuantidade())))
    			.reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public BigDecimal calcularPnlTotalDoUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        return posicaoRepository.findByUsuario(usuario).stream()
                .map(Posicao::getPnl)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
