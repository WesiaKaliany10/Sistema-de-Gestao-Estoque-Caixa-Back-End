package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.dto;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.Perfil;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.StatusUsuario;

public record UsuarioResponseAdmin(
        Long id,
        String nome,
        String email,
        Perfil perfil,
        StatusUsuario status)
{ }
