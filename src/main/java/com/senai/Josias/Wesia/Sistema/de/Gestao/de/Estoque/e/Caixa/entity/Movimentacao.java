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

}
