package br.edu.unifio.labacervo.servicos;

import br.edu.unifio.labacervo.entidades.*;
import br.edu.unifio.labacervo.dto.SolicitanteEntrada;
import br.edu.unifio.labacervo.repositorios.*;
import br.edu.unifio.labacervo.erros.FalhaDominio;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SolicitanteServico {
    private final SolicitanteRepositorio repositorio;

    public List<Solicitante> listar() {
        return repositorio.findAll();
    }

    public Solicitante buscar(long id) {
        return repositorio.findById(id).orElseThrow(() -> FalhaDominio.ausente("Solicitante", id));
    }

    @Transactional
    public Solicitante criar(SolicitanteEntrada dados) {
        var registro = new Solicitante();
        aplicar(registro, dados);
        return repositorio.save(registro);
    }

    @Transactional
    public Solicitante atualizar(long id, SolicitanteEntrada dados) {
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

    private void aplicar(Solicitante registro, SolicitanteEntrada dados) {
        registro.setNome(dados.nome().strip());
        registro.setMatricula(dados.matricula().strip());
    }
}
