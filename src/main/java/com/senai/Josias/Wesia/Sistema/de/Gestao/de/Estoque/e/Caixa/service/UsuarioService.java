package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.service;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.dto.UsuarioRequest;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.dto.UsuarioResponseAdmin;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.dto.UsuarioResponseOperador;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.entity.Usuario;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.Perfil;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.StatusUsuario;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.exceptions.BusinessException;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.exceptions.EmailConflictException;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.exceptions.ResourceNotFoundException;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.mapper.UsuarioMapper;
import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }


    public List<UsuarioResponseAdmin> listarTodosUsuarios(Perfil perfil, StatusUsuario statusUsuario) {

        List<Usuario> lista;

        if(perfil != null && statusUsuario == StatusUsuario.ATIVO){
            lista = repository.findByPerfilContainingIgnoreCaseOrderByNomeAsc(perfil);
        } else if (statusUsuario == StatusUsuario.INATIVO){
            lista = repository.findByStatusOrderByNomeByAsc(statusUsuario);
        } else {
            lista = repository.findAllByOrderByNomeAsc();
        }
        return  lista.stream().map(UsuarioMapper::toResponseAdmin).toList();
    }

    @Transactional
    public UsuarioResponseOperador cadastrarOperador(UsuarioRequest usuarioRequest) {

        validarRegras(usuarioRequest);

        var existente = repository.findByEmailIgnoreCase(usuarioRequest.email());

        if (existente.isPresent()) {
            throw new EmailConflictException("E-mail já cadastrado");
        }

        var novo = UsuarioMapper.toEntity(usuarioRequest);

        var salvo = repository.save(novo);

        return UsuarioMapper.toResponseOperador(salvo);
    }

    @Transactional
    public UsuarioResponseAdmin cadastrarAdmin(UsuarioRequest usuarioRequest) {

        validarRegras(usuarioRequest);

        var existente = repository.findByEmailIgnoreCase(usuarioRequest.email());

        if (existente.isPresent()) {
            throw new EmailConflictException("E-mail já cadastrado");
        }

        var novo = UsuarioMapper.toEntity(usuarioRequest);
        var salvo = repository.save(novo);

        return UsuarioMapper.toResponseAdmin(salvo);
    }

    @Transactional
    public UsuarioResponseOperador atualizarOperador(Long id, UsuarioRequest usuarioRequest) {

        validarRegras(usuarioRequest);

        var existente = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));

        if (!existente.getEmail().equalsIgnoreCase(usuarioRequest.email()) && repository.existsByEmailIgnoreCase(usuarioRequest.email())) {
            throw new EmailConflictException("E-mail já cadastrado por outro usuário");
        }

        atualizar(usuarioRequest, existente);

        var salvo = repository.save(existente);
        return UsuarioMapper.toResponseOperador(salvo);

    }

    @Transactional
    public UsuarioResponseAdmin atualizarAdmin(Long id, UsuarioRequest usuarioRequest) {

        validarRegras(usuarioRequest);

        var existente = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));

        if (!existente.getEmail().equalsIgnoreCase(usuarioRequest.email()) && repository.existsByEmailIgnoreCase(usuarioRequest.email())) {
            throw new EmailConflictException("E-mail já cadastrado por outro usuário");
        }

        atualizar(usuarioRequest, existente);

        var salvo = repository.save(existente);
        return UsuarioMapper.toResponseAdmin(salvo);

    }

    @Transactional
    public UsuarioResponseOperador inativarOperador(Long id) {
        var existente = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));
        existente.setStatus(StatusUsuario.INATIVO);
        var salvo = repository.save(existente);
        return UsuarioMapper.toResponseOperador(salvo);
    }

    @Transactional
    public UsuarioResponseAdmin inativarAdmin(Long id) {
        var existente = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));
        existente.setStatus(StatusUsuario.INATIVO);
        var salvo = repository.save(existente);
        return UsuarioMapper.toResponseAdmin(salvo);
    }

    private void atualizar(UsuarioRequest usuarioRequest, Usuario usuario) {
        usuario.setEmail(usuarioRequest.email());
        usuario.setNome(usuarioRequest.nome());
        usuario.setPerfil(usuarioRequest.perfil());
    }

    private void validarRegras(UsuarioRequest usuarioRequest) {

        if(usuarioRequest.nome().isBlank() || usuarioRequest.email().isBlank()) {
            throw new BusinessException("Campos não podem conter apenas espaços em branco");
        }

        if(usuarioRequest.senha().isBlank() || usuarioRequest.senha().length() < 8) {
            throw new BusinessException("Senha deve seguir política mínima (8 caracteres, 1 letra maiúscula e 1 número).");
        }

    }

}
