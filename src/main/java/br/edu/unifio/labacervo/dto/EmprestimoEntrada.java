package br.edu.unifio.labacervo.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.*;

public record EmprestimoEntrada(
        @NotNull @Positive Long equipamentoId,
        @NotNull @Positive Long solicitanteId,
        @NotNull LocalDate prazo,
        @NotNull @Size(max = 240) String observacao
) {}
