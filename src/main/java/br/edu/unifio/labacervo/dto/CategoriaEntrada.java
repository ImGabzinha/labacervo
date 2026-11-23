package br.edu.unifio.labacervo.dto;

import jakarta.validation.constraints.*;

public record CategoriaEntrada(
        @NotBlank @Size(max = 100) String nome,
        @NotBlank @Size(max = 240) String descricao
) {}
