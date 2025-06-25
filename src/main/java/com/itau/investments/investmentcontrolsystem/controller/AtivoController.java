package com.itau.investments.investmentcontrolsystem.controller;

import com.itau.investments.investmentcontrolsystem.model.Ativo;
import com.itau.investments.investmentcontrolsystem.service.AtivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ativos")
@RequiredArgsConstructor
public class AtivoController {

    private final AtivoService ativoService;

    @GetMapping
    public ResponseEntity<List<Ativo>> listarTodos() {
        return ResponseEntity.ok(ativoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ativo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ativoService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Ativo> buscarPorCodigo(@RequestParam String codigo) {
        return ResponseEntity.ok(ativoService.buscarPorCodigo(codigo));
    }

    @PostMapping
    public ResponseEntity<Ativo> salvar(@RequestBody Ativo ativo) {
        return ResponseEntity.ok(ativoService.salvar(ativo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ativoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
