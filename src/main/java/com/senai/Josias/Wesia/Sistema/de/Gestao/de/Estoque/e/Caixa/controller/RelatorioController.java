package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.controller;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity.Venda;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.service.RelatorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
@CrossOrigin("*")
public class RelatorioController {

    private final RelatorioService service;

    public RelatorioController(RelatorioService service) {
        this.service = service;
    }

    @GetMapping("/vendas")
    public ResponseEntity<List<Venda>> listarVendas(@RequestParam(required = false) Date inicio,
                                                    @RequestParam(required = false) Date fim,
                                                    @RequestParam(required = false) BigDecimal valorMin,
                                                    @RequestParam(required = false) BigDecimal valorMax) {
        return ResponseEntity.ok(service.listarVendas(inicio, fim, valorMin, valorMax));
    }

    @GetMapping("/vendas/total")
    public ResponseEntity<BigDecimal> totalVendas(@RequestParam(required = false) Date inicio,
                                                  @RequestParam(required = false) Date fim,
                                                  @RequestParam(required = false) BigDecimal valorMin,
                                                  @RequestParam(required = false) BigDecimal valorMax) {
        List<Venda> vendas = service.listarVendas(inicio, fim, valorMin, valorMax);
        return ResponseEntity.ok(service.totalVendas(vendas));
    }

    @GetMapping("/vendas/itens")
    public ResponseEntity<Integer> totalItens(@RequestParam(required = false) Date inicio,
                                              @RequestParam(required = false) Date fim,
                                              @RequestParam(required = false) BigDecimal valorMin,
                                              @RequestParam(required = false) BigDecimal valorMax) {
        List<Venda> vendas = service.listarVendas(inicio, fim, valorMin, valorMax);
        return ResponseEntity.ok(service.totalItens(vendas));
    }
}
