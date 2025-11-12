package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "vendas")
@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valorTotal;

    private BigDecimal valorRecebido;

    private BigDecimal troco;

    private Date dataVenda;


}
