package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.service;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity.ItemVenda;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity.Produto;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity.Venda;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.TipoMovimentacao;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.exceptions.BusinessException;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.repository.ItemVendaRepository;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.repository.ProdutoRepository;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.repository.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ItemVendaRepository itemVendaRepository;
    private final ProdutoRepository produtoRepository;
    private final MovimentacaoService movimentacaoService;

    public VendaService(VendaRepository vendaRepository,
                        ItemVendaRepository itemVendaRepository,
                        ProdutoRepository produtoRepository,
                        MovimentacaoService movimentacaoService) {
        this.vendaRepository = vendaRepository;
        this.itemVendaRepository = itemVendaRepository;
        this.produtoRepository = produtoRepository;
        this.movimentacaoService = movimentacaoService;
    }

    @Transactional
    public Venda registrarVenda(List<ItemVenda> itens, BigDecimal valorRecebido) {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemVenda item : itens) {
            Produto produto = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new BusinessException("Produto não encontrado"));
            if (item.getQuantidade() > produto.getQuantidadeEmEstoque()) {
                throw new BusinessException("Quantidade insuficiente em estoque para o produto " + produto.getNomeProduto());
            }

            BigDecimal subtotal = produto.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()));
            total = total.add(subtotal);

            item.setPrecoUnitario(produto.getPrecoUnitario());
            item.setProduto(produto);
        }

        if (valorRecebido.compareTo(total) < 0) {
            throw new BusinessException("Valor recebido menor que total da venda");
        }

        Venda venda = new Venda();
        venda.setValorTotal(total);
        venda.setValorRecebido(valorRecebido);
        venda.setTroco(valorRecebido.subtract(total));
        venda.setDataVenda(new Date());
        Venda vendaSalva = vendaRepository.save(venda);

        for (ItemVenda item : itens) {
            item.setVenda(vendaSalva);
            itemVendaRepository.save(item);
            movimentacaoService.registrarMovimentacao(
                    item.getProduto().getId(),
                    TipoMovimentacao.SAIDA,
                    item.getQuantidade()
            );
        }

        return vendaSalva;
    }

    public List<Venda> listarVendas() {
        return vendaRepository.findAll();
    }
}
