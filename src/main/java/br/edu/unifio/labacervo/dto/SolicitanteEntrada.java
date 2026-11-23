package br.edu.unifio.labacervo.dto;

import jakarta.validation.constraints.*;

public record SolicitanteEntrada(
        @NotBlank @Size(max = 100) String nome,
        @NotBlank @Size(max = 30) String matricula
) {}
