package br.edu.unifio.labacervo.repositorios;

import br.edu.unifio.labacervo.entidades.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EquipamentoRepositorio extends JpaRepository<Equipamento, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select x from Equipamento x where x.id = :id")
    Optional<Equipamento> bloquear(@Param("id") long id);

    @Query("""
        select e from Equipamento e where e.laboratorio.id = :laboratorioId
        and not exists (select p.id from Emprestimo p
                        where p.equipamento = e and p.devolucao is null)
        order by e.patrimonio
        """)
    List<Equipamento> disponiveis(@Param("laboratorioId") long laboratorioId);
}
