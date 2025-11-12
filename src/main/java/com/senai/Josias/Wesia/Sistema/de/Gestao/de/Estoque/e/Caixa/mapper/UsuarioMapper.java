package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.mapper;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.dto.UsuarioRequest;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.dto.UsuarioResponseAdmin;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.dto.UsuarioResponseOperador;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity.Usuario;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.Perfil;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.StatusUsuario;

public final class UsuarioMapper {

    private UsuarioMapper() {}

    public static Usuario toEntity(UsuarioRequest dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setPerfil(Perfil.OPERADOR);
        usuario.setStatus(StatusUsuario.ATIVO);
        return usuario;
    }

    public static UsuarioResponseOperador toResponseOperador(Usuario operador) {
        return new UsuarioResponseOperador(
                operador.getId(),
                operador.getNome(),
                operador.getEmail()
        );
    }

    public static UsuarioResponseAdmin toResponseAdmin(Usuario admin) {
        return new UsuarioResponseAdmin(
                admin.getId(),
                admin.getNome(),
                admin.getEmail(),
                admin.getPerfil(),
                admin.getStatus()
        );
    }

}
