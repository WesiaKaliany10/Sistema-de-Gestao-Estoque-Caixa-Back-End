package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.service;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity.Venda;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    private final VendaRepository vendaRepository;

    public RelatorioService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    public List<Venda> listarVendas(Date inicio, Date fim, BigDecimal valorMin, BigDecimal valorMax) {
        List<Venda> vendas = vendaRepository.findAll();
        return vendas.stream().filter(v ->
                (inicio == null || !v.getDataVenda().before(inicio)) &&
                        (fim == null || !v.getDataVenda().after(fim)) &&
                        (valorMin == null || v.getValorTotal().compareTo(valorMin) >= 0) &&
                        (valorMax == null || v.getValorTotal().compareTo(valorMax) <= 0)
        ).collect(Collectors.toList());
    }

    public BigDecimal totalVendas(List<Venda> vendas) {
        return vendas.stream().map(Venda::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Integer totalItens(List<Venda> vendas) {
        return vendas.stream().map(v -> v.getItens().size())
                .reduce(0, Integer::sum);
    }
}
