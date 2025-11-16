package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.service;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity.Movimentacao;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity.Produto;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.TipoMovimentacao;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.repository.MovimentacaoRepository;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository, ProdutoRepository produtoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Movimentacao registrarMovimentacao(Long produtoId, TipoMovimentacao tipo, int quantidade) {
        Produto produto = produtoRepository.findById(produtoId).orElseThrow();
        if(tipo == TipoMovimentacao.SAIDA && produto.getQuantidadeEmEstoque() < quantidade) {
            throw new RuntimeException("Estoque insuficiente");
        }
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setProduto(produto);
        movimentacao.setTipoMovimentacao(tipo);
        movimentacao.setQuantidade(quantidade);
        movimentacao.setDataMovimentacao(java.time.LocalDateTime.now());

        if(tipo == TipoMovimentacao.ENTRADA) {
            produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() + quantidade);
        } else {
            produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - quantidade);
        }

        produtoRepository.save(produto);
        return movimentacaoRepository.save(movimentacao);
    }

    public List<Movimentacao> listarMovimentacoes() {
        return movimentacaoRepository.findAll();
    }
}
