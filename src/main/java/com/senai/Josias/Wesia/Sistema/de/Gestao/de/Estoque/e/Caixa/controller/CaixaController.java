package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.controller;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity.ItemVenda;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity.Venda;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.service.CaixaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/caixa")
@CrossOrigin("*")
public class CaixaController {

    private final CaixaService service;

    public CaixaController(CaixaService service) {
        this.service = service;
    }

    @PostMapping("/vendas")
    public ResponseEntity<Venda> registrarVenda(@RequestBody List<ItemVenda> itens,
                                                @RequestParam BigDecimal valorRecebido) {
        return ResponseEntity.ok(service.registrarVenda(itens, valorRecebido));
    }
}
