package com.itau.investments.investmentcontrolsystem.controller;

import com.itau.investments.investmentcontrolsystem.model.Posicao;
import com.itau.investments.investmentcontrolsystem.service.PosicaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/posicoes")
@RequiredArgsConstructor
public class PosicaoController {

    private final PosicaoService posicaoService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Posicao>> listarPosicoesDoUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(posicaoService.listarPosicoesPorUsuario(usuarioId));
    }

    @GetMapping
    public ResponseEntity<Posicao> buscarPorUsuarioEAtivo(
            @RequestParam Long usuarioId,
            @RequestParam Long ativoId
    ) {
        return ResponseEntity.ok(posicaoService.buscarPosicaoPorUsuarioEAtivo(usuarioId, ativoId));
    }

    @GetMapping("/pnl-total")
    public ResponseEntity<BigDecimal> calcularPnlTotal(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(posicaoService.calcularPnlTotalDoUsuario(usuarioId));
    }

    @GetMapping("/total-investido")
    public ResponseEntity<BigDecimal> calcularTotalInvestido(
            @RequestParam Long usuarioId,
            @RequestParam Long ativoId
    ) {
        return ResponseEntity.ok(posicaoService.calcularTotalInvestidoPorAtivo(usuarioId, ativoId));
    }
}
