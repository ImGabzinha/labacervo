package br.edu.unifio.labacervo.servicos;

import br.edu.unifio.labacervo.entidades.*;
import br.edu.unifio.labacervo.dto.EmprestimoEntrada;
import br.edu.unifio.labacervo.repositorios.*;
import br.edu.unifio.labacervo.erros.FalhaDominio;
import java.util.List;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmprestimoServico {
    private final EmprestimoRepositorio repositorio;
    private final EquipamentoRepositorio equipamentos;
    private final SolicitanteRepositorio solicitantes;

    public List<Emprestimo> listar() {
        return repositorio.findAll();
    }

    public Emprestimo buscar(long id) {
        return repositorio.findById(id).orElseThrow(() -> FalhaDominio.ausente("Empréstimo", id));
    }

    @Transactional
    public Emprestimo criar(EmprestimoEntrada dados) {
        var registro = new Emprestimo();
        var equipamento = equipamentos.bloquear(dados.equipamentoId())
                .orElseThrow(() -> FalhaDominio.ausente("Equipamento", dados.equipamentoId()));
        if (repositorio.existsByEquipamentoIdAndDevolucaoIsNull(equipamento.getId())) {
            throw FalhaDominio.conflito("Este equipamento já possui empréstimo em aberto.");
        }
        registro.setEquipamento(equipamento);
        registro.setSolicitante(solicitantes.findById(dados.solicitanteId())
                .orElseThrow(() -> FalhaDominio.ausente("Solicitante", dados.solicitanteId())));
        registro.setRetirada(LocalDate.now());
        aplicar(registro, dados);
        return repositorio.save(registro);
    }

    @Transactional
    public Emprestimo atualizar(long id, EmprestimoEntrada dados) {
        var registro = bloquear(id);
        exigirAberto(registro);
        if (!registro.getEquipamento().getId().equals(dados.equipamentoId())
                || !registro.getSolicitante().getId().equals(dados.solicitanteId())) {
            throw FalhaDominio.conflito("A retirada mantém o equipamento e o solicitante originais.");
        }
        aplicar(registro, dados);
        return registro;
    }

    @Transactional
    public void excluir(long id) {
        var registro = bloquear(id);
        if (registro.getDevolucao() == null) throw FalhaDominio.conflito("Registre a devolução antes de excluir.");
        repositorio.delete(registro);
        repositorio.flush();
    }

    private void aplicar(Emprestimo registro, EmprestimoEntrada dados) {
        if (dados.prazo().isBefore(registro.getRetirada())) {
            throw FalhaDominio.conflito("O prazo deve ser igual ou posterior à retirada.");
        }
        registro.setPrazo(dados.prazo());
        registro.setObservacao(dados.observacao().strip());
    }

    public List<Emprestimo> historico(long solicitanteId) {
        if (!solicitantes.existsById(solicitanteId)) throw FalhaDominio.ausente("Solicitante", solicitanteId);
        return repositorio.findBySolicitanteIdOrderByIdDesc(solicitanteId);
    }

    @Transactional
    public Emprestimo devolver(long id) {
        var registro = bloquear(id);
        exigirAberto(registro);
        registro.setDevolucao(LocalDate.now());
        return registro;
    }

    private Emprestimo bloquear(long id) {
        return repositorio.bloquear(id).orElseThrow(() -> FalhaDominio.ausente("Empréstimo", id));
    }

    private void exigirAberto(Emprestimo registro) {
        if (registro.getDevolucao() != null) throw FalhaDominio.conflito("Este empréstimo já foi devolvido.");
    }
}
