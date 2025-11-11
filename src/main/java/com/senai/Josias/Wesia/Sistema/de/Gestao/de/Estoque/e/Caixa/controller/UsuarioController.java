package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.controller;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.dto.UsuarioResponseAdmin;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.Perfil;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.StatusUsuario;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.repository.UsuarioRepository;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<UsuarioResponseAdmin>> listarUsuarios(
            @RequestParam(required = false) Perfil perfil,
            @RequestParam(required = false) StatusUsuario statusUsuario) {
            var listaUsuarios = service.listarTodosUsuarios(perfil,statusUsuario);
            return ResponseEntity.ok(listaUsuarios);
    }


}
