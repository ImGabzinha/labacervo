package br.edu.unifio.labacervo.servicos;

import br.edu.unifio.labacervo.entidades.*;
import br.edu.unifio.labacervo.dto.LaboratorioEntrada;
import br.edu.unifio.labacervo.repositorios.*;
import br.edu.unifio.labacervo.erros.FalhaDominio;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LaboratorioServico {
    private final LaboratorioRepositorio repositorio;

    public List<Laboratorio> listar() {
        return repositorio.findAll();
    }

    public Laboratorio buscar(long id) {
        return repositorio.findById(id).orElseThrow(() -> FalhaDominio.ausente("Laboratório", id));
    }

    @Transactional
    public Laboratorio criar(LaboratorioEntrada dados) {
        var registro = new Laboratorio();
        aplicar(registro, dados);
        return repositorio.save(registro);
    }

    @Transactional
    public Laboratorio atualizar(long id, LaboratorioEntrada dados) {
        var registro = buscar(id);
        aplicar(registro, dados);
        return registro;
    }

    @Transactional
    public void excluir(long id) {
        var registro = buscar(id);
        repositorio.delete(registro);
        repositorio.flush();
    }

    private void aplicar(Laboratorio registro, LaboratorioEntrada dados) {
        registro.setNome(dados.nome().strip());
        registro.setSala(dados.sala().strip());
    }
}
