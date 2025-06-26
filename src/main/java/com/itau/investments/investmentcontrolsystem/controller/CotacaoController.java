package com.itau.investments.investmentcontrolsystem.controller;

import com.itau.investments.investmentcontrolsystem.model.Cotacao;
import com.itau.investments.investmentcontrolsystem.service.CotacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cotacoes")
@RequiredArgsConstructor
public class CotacaoController {

    private final CotacaoService cotacaoService;

    @PostMapping
    public ResponseEntity<Cotacao> registrarCotacao(
            @RequestParam Long ativoId,
            @RequestParam BigDecimal precoUnitario
    ) {
        Cotacao cotacao = cotacaoService.registrarCotacao(ativoId, precoUnitario);
        return ResponseEntity.ok(cotacao);
    }

    @GetMapping("/ultima")
    public ResponseEntity<Cotacao> buscarUltimaCotacao(@RequestParam Long ativoId) {
        return ResponseEntity.ok(cotacaoService.buscarUltimaCotacao(ativoId));
    }

    @GetMapping("/historico")
    public ResponseEntity<List<Cotacao>> listarHistorico(@RequestParam Long ativoId) {
        return ResponseEntity.ok(cotacaoService.listarHistoricoPorAtivo(ativoId));
    }
}
