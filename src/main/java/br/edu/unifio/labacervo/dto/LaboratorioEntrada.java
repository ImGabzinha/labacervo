package br.edu.unifio.labacervo.dto;

import jakarta.validation.constraints.*;

public record LaboratorioEntrada(
        @NotBlank @Size(max = 100) String nome,
        @NotBlank @Size(max = 40) String sala
) {}
