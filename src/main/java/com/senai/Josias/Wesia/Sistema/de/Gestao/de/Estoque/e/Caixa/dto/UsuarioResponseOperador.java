package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.dto;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.Perfil;

public record UsuarioResponseOperador(
        Long id,
        String nome,
        String email,
        Perfil perfil)
{ }
