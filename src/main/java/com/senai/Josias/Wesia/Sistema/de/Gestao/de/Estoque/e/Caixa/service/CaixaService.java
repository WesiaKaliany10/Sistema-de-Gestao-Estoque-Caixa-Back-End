package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.service;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity.ItemVenda;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity.Produto;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity.Venda;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.repository.ProdutoRepository;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.repository.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class CaixaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;

    public CaixaService(VendaRepository vendaRepository, ProdutoRepository produtoRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Venda registrarVenda(List<ItemVenda> itens, BigDecimal valorRecebido) {
        Venda venda = new Venda();
        venda.setDataVenda(new Date());
        BigDecimal total = BigDecimal.ZERO;

        for(ItemVenda item : itens) {
            Produto produto = produtoRepository.findById(item.getProduto().getId()).orElseThrow();
            if(produto.getQuantidadeEmEstoque() < item.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNomeProduto());
            }
            produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - item.getQuantidade());
            produtoRepository.save(produto);
            item.setPrecoUnitario(produto.getPrecoUnitario());
            item.setVenda(venda);
            total = total.add(produto.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }

        venda.setValorTotal(total);
        venda.setValorRecebido(valorRecebido);
        venda.setTroco(valorRecebido.subtract(total));
        vendaRepository.save(venda);
        return venda;
    }
}
