package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.repository;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByCodigoProduto(String codigoProduto);
    boolean existsByCodigoProduto(String codigoProduto);
}
