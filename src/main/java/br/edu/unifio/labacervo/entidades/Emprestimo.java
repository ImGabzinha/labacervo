package br.edu.unifio.labacervo.entidades;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Equipamento equipamento;

    @ManyToOne(optional = false)
    private Solicitante solicitante;

    @Column(nullable = false)
    private LocalDate retirada;

    @Column(nullable = false)
    private LocalDate prazo;

    @Column
    private LocalDate devolucao;

    @Column(nullable = false, length = 240)
    private String observacao;
}
