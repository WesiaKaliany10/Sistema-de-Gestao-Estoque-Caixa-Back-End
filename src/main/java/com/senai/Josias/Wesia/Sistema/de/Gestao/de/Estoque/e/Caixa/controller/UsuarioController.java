package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.controller;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.dto.UsuarioRequest;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.dto.UsuarioResponseAdmin;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.dto.UsuarioResponseOperador;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.Perfil;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.StatusUsuario;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
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

    @PostMapping
    public ResponseEntity<UsuarioResponseOperador> cadastrarAdmin(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        var result = service.cadastrarOperador(usuarioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }



        return ResponseEntity.ok(service.listarTodosUsuarios(perfil, statusUsuario));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseOperador> cadastrarOperador(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrarOperador(usuarioRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseAdmin> atualizarUsuario(@PathVariable Long id,
                                                                 @Valid @RequestBody UsuarioRequest usuarioRequest) {
        return ResponseEntity.ok(service.atualizarUsuario(id, usuarioRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        service.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
