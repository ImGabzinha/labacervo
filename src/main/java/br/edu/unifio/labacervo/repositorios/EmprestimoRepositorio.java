package br.edu.unifio.labacervo.repositorios;

import br.edu.unifio.labacervo.entidades.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmprestimoRepositorio extends JpaRepository<Emprestimo, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select x from Emprestimo x where x.id = :id")
    Optional<Emprestimo> bloquear(@Param("id") long id);

    boolean existsByEquipamentoIdAndDevolucaoIsNull(long equipamentoId);
    List<Emprestimo> findBySolicitanteIdOrderByIdDesc(long solicitanteId);
}
