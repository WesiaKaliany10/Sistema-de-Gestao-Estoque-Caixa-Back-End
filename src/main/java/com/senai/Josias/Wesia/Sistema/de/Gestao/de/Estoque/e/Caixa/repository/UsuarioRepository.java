package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.repository;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity.Usuario;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.Perfil;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.StatusUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailIgnoreCase(String email);
    List<Usuario> findByPerfilContainingIgnoreCaseOrderByNomeAsc(Perfil perfil);
    List<Usuario> findByStatusOrderByNomeByAsc(StatusUsuario statusUsuario);
    List<Usuario> findAllByOrderByNomeAsc();
    boolean existsByEmailIgnoreCase(String email);
}
