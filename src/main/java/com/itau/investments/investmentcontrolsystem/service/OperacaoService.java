package com.itau.investments.investmentcontrolsystem.service;

import java.math.*;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itau.investments.investmentcontrolsystem.enums.TipoOperacao;
import com.itau.investments.investmentcontrolsystem.model.*;
import com.itau.investments.investmentcontrolsystem.repository.*;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OperacaoService {
	
	private final OperacaoRepository operacaoRepository;
	private final UsuarioRepository usuarioRepository;
	private final AtivoRepository ativoRepository;
	private final PosicaoRepository posicaoRepository;
	private final CotacaoRepository cotacaoRepository;
	
	@Transactional
	public Operacao registrarOperacao(Long usuarioId, Long ativoId, Integer quantidade, 
										BigDecimal precoUnitario, TipoOperacao tipo, BigDecimal corretagem) {
		
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
		
		Ativo ativo = ativoRepository.findById(ativoId)
				.orElseThrow(() -> new EntityNotFoundException("Ativo não encontrado."));
		
		Operacao operacao = Operacao.builder()
				.usuario(usuario)
				.ativo(ativo)
				.quantidade(quantidade)
				.precoUnitario(precoUnitario)
				.tipoOperacao(tipo)
				.corretagem(corretagem)
				.dataHora(LocalDateTime.now())
				.build();
		
		operacaoRepository.save(operacao);
		atualizarPosicao(usuario, ativo, operacao);
		
		return operacao;
	}
	
	public List<Operacao> listarTodas() {
		return operacaoRepository.findAll();
	}
	
	public List<Operacao> listarPorUsuario(Long usuarioId) {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
		return operacaoRepository.findByUsuarioOrderByDataHoraDesc(usuario);
	}
	
	public List<Operacao> listarPorUsuarioEAtivo(Long usuarioId, Long ativoId) {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
		Ativo ativo = ativoRepository.findById(ativoId)
				.orElseThrow(() -> new EntityNotFoundException("Ativo não encontrado."));
		return operacaoRepository.findByUsuarioAndAtivo(usuario, ativo);
	}
	
	public List<Operacao> listarUltimos30Dias(Long usuarioId, Long ativoId) {
		LocalDateTime dataInicio = LocalDateTime.now().minusDays(30);
		return operacaoRepository.findByUsuarioIdAndAtivoIdAndDataHoraAfter(usuarioId, ativoId, dataInicio);
	}
	
	public BigDecimal calcularTotalCorretagem(Long usuarioId) {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
		BigDecimal total = operacaoRepository.totalCorretagemPorUsuario(usuario);
		return total != null ? total : BigDecimal.ZERO;
	}
	
	private void atualizarPosicao(Usuario usuario, Ativo ativo, Operacao operacao) {
		Posicao posicao = posicaoRepository.findByUsuarioAndAtivo(usuario, ativo)
				.orElse(Posicao.builder()
						.id(new PosicaoId(usuario.getId(), ativo.getId()))
						.usuario(usuario)
						.ativo(ativo)
						.quantidade(0)
						.precoMedio(BigDecimal.ZERO)
						.pnl(BigDecimal.ZERO)
						.build());
		
		int novaQuantidade = posicao.getQuantidade();
		BigDecimal novoPrecoMedio = posicao.getPrecoMedio();
		
		if (operacao.getTipoOperacao() == TipoOperacao.COMPRA) {
			int totalQuantidade = posicao.getQuantidade() + operacao.getQuantidade();
			BigDecimal totalInvestido = novoPrecoMedio.multiply(BigDecimal.valueOf(posicao.getQuantidade()))
					.add(operacao.getPrecoUnitario().multiply(BigDecimal.valueOf(operacao.getQuantidade())));
			novaQuantidade = totalQuantidade;
			novoPrecoMedio = totalInvestido.divide(BigDecimal.valueOf(totalQuantidade), 8, RoundingMode.HALF_UP);
		} else {
			novaQuantidade -= operacao.getQuantidade();
			
			Cotacao ultimaCotacao = cotacaoRepository.findTop1ByAtivoOrderByDataHoraDesc(ativo);
			if (ultimaCotacao != null) {
				BigDecimal precoVenda = operacao.getPrecoUnitario();
				BigDecimal lucroPrejuizo = precoVenda
						.subtract(posicao.getPrecoMedio())
						.multiply(BigDecimal.valueOf(operacao.getQuantidade()));
				
				posicao.setPnl(posicao.getPnl().add(lucroPrejuizo));
			}
		}
		
		posicao.setQuantidade(novaQuantidade);
		posicao.setPrecoMedio(novoPrecoMedio);
		
		posicaoRepository.save(posicao);
	}
}
