package br.edu.unifio.labacervo.dto;

import jakarta.validation.constraints.*;

public record EquipamentoEntrada(
        @NotBlank @Size(max = 100) String nome,
        @NotBlank @Size(max = 30) String patrimonio,
        @NotNull @Positive Long categoriaId,
        @NotNull @Positive Long laboratorioId
) {}
