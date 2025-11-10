package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.dto;

import com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.enums.Perfil;
import jakarta.validation.constraints.*;

public record UsuarioRequest (
    @NotBlank
    String nome,

    @NotBlank
    @Email(message = "E-mail inválido.")
    String email,

    @NotBlank
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres.")
    String senha,

    @NotNull
    Perfil perfil

    ){

}
