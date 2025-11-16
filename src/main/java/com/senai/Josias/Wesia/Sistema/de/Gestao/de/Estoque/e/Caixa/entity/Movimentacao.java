package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.TipoMovimentacao;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "movimentacoes")
@Entity
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    private LocalDateTime dataMovimentacao;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipoMovimentacao;

    private int quantidade;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public TipoMovimentacao getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

}
