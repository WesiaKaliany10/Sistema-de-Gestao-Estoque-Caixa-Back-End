package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.controller;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity.Movimentacao;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.TipoMovimentacao;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.service.MovimentacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentacoes")
@CrossOrigin("*")
public class MovimentacaoController {

    private final MovimentacaoService service;

    public MovimentacaoController(MovimentacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Movimentacao> registrarMovimentacao(@RequestParam Long produtoId,
                                                              @RequestParam TipoMovimentacao tipo,
                                                              @RequestParam int quantidade) {
        return ResponseEntity.ok(service.registrarMovimentacao(produtoId, tipo, quantidade));
    }

    @GetMapping
    public ResponseEntity<List<Movimentacao>> listarMovimentacoes() {
        return ResponseEntity.ok(service.listarMovimentacoes());
    }
}
