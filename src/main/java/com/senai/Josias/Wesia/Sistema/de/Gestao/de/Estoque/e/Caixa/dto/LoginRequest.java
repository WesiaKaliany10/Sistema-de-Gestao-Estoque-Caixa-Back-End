package com.senai.Josias.Wesia.Sistema.de.Gestao.de.Estoque.e.Caixa.dto;

import jakarta.validation.constraints.*;

public record LoginRequest(
        @NotBlank(message = "O Email é obrigatório.")
        @Email(message = "E-mail invalido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        String senha
) {
}
