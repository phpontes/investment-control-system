package com.itau.investments.investmentcontrolsystem.controller;

import com.itau.investments.investmentcontrolsystem.enums.TipoOperacao;
import com.itau.investments.investmentcontrolsystem.model.Operacao;
import com.itau.investments.investmentcontrolsystem.service.OperacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/operacoes")
@RequiredArgsConstructor
public class OperacaoController {

    private final OperacaoService operacaoService;

    @PostMapping
    public ResponseEntity<Operacao> registrarOperacao(
            @RequestParam Long usuarioId,
            @RequestParam Long ativoId,
            @RequestParam Integer quantidade,
            @RequestParam BigDecimal precoUnitario,
            @RequestParam TipoOperacao tipo,
            @RequestParam(required = false, defaultValue = "0.00") BigDecimal corretagem
    ) {
        Operacao operacao = operacaoService.registrarOperacao(
                usuarioId, ativoId, quantidade, precoUnitario, tipo, corretagem);
        return ResponseEntity.ok(operacao);
    }

    @GetMapping
    public ResponseEntity<List<Operacao>> listarTodas() {
        return ResponseEntity.ok(operacaoService.listarTodas());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Operacao>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(operacaoService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/ativo/{ativoId}")
    public ResponseEntity<List<Operacao>> listarPorUsuarioEAtivo(@PathVariable Long usuarioId, @PathVariable Long ativoId) {
        return ResponseEntity.ok(operacaoService.listarPorUsuarioEAtivo(usuarioId, ativoId));
    }

    @GetMapping("/ultimos30dias")
    public ResponseEntity<List<Operacao>> listarUltimos30Dias(
            @RequestParam Long usuarioId,
            @RequestParam Long ativoId
    ) {
        return ResponseEntity.ok(operacaoService.listarUltimos30Dias(usuarioId, ativoId));
    }

    @GetMapping("/corretagem/total")
    public ResponseEntity<BigDecimal> totalCorretagem(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(operacaoService.calcularTotalCorretagem(usuarioId));
    }
}
